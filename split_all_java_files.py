#!/usr/bin/env python3
"""
Split Java files with multiple public classes into separate files.
Each public class/interface/enum gets its own file.
"""

import os
import re
from pathlib import Path
import shutil

def extract_public_types(content):
    """Extract all public types (classes, interfaces, enums) from Java content."""
    # Patterns to match public declarations
    patterns = [
        r'public\s+(?:static\s+)?(?:final\s+)?class\s+(\w+)',
        r'public\s+(?:static\s+)?(?:final\s+)?interface\s+(\w+)',
        r'public\s+(?:static\s+)?(?:final\s+)?enum\s+(\w+)',
        r'public\s+(?:static\s+)?(?:abstract\s+)?class\s+(\w+)',
    ]
    
    public_types = []
    for pattern in patterns:
        matches = re.finditer(pattern, content)
        for match in matches:
            type_name = match.group(1)
            public_types.append((type_name, match.start()))
    
    # Sort by position in file
    public_types.sort(key=lambda x: x[1])
    return [t[0] for t in public_types]

def extract_type_content(content, type_name, next_type_pos=None):
    """Extract the content for a specific type."""
    # Find the start of this type
    patterns = [
        rf'((?:\/\*\*[\s\S]*?\*\/\s*)?(?:@\w+(?:\([^)]*\))?\s*)*public\s+(?:static\s+)?(?:final\s+)?(?:abstract\s+)?(?:class|interface|enum)\s+{type_name})',
    ]
    
    for pattern in patterns:
        match = re.search(pattern, content)
        if match:
            start_pos = match.start()
            
            # Find the end of this type by counting braces
            brace_count = 0
            in_string = False
            in_char = False
            in_comment = False
            in_line_comment = False
            i = match.end()
            
            # Skip to the opening brace
            while i < len(content) and content[i] != '{':
                i += 1
            
            if i < len(content):
                brace_count = 1
                i += 1
                
                while i < len(content) and brace_count > 0:
                    if i < len(content) - 1:
                        two_char = content[i:i+2]
                        if not in_string and not in_char and not in_comment and two_char == '//':
                            in_line_comment = True
                        elif not in_string and not in_char and not in_line_comment and two_char == '/*':
                            in_comment = True
                            i += 1  # Skip the *
                        elif in_comment and two_char == '*/':
                            in_comment = False
                            i += 1  # Skip the /
                    
                    char = content[i]
                    
                    if in_line_comment and char == '\n':
                        in_line_comment = False
                    elif not in_comment and not in_line_comment:
                        if char == '"' and not in_char and (i == 0 or content[i-1] != '\\'):
                            in_string = not in_string
                        elif char == "'" and not in_string and (i == 0 or content[i-1] != '\\'):
                            in_char = not in_char
                        elif not in_string and not in_char:
                            if char == '{':
                                brace_count += 1
                            elif char == '}':
                                brace_count -= 1
                    
                    i += 1
                
                # Extract the complete type definition
                end_pos = i
                
                # If there's a next type and we've gone past it, trim back
                if next_type_pos and end_pos > next_type_pos:
                    end_pos = next_type_pos
                
                return content[start_pos:end_pos].strip()
    
    return None

def split_java_file(file_path):
    """Split a Java file with multiple public types into separate files."""
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Extract package and imports
    package_match = re.search(r'package\s+([^;]+);', content)
    if not package_match:
        print(f"No package found in {file_path}")
        return 0
    
    package_line = package_match.group(0)
    
    # Find all imports
    import_pattern = r'import\s+(?:static\s+)?[^;]+;'
    imports = re.findall(import_pattern, content)
    import_block = '\n'.join(imports) if imports else ''
    
    # Get all public types
    public_types = extract_public_types(content)
    
    if len(public_types) <= 1:
        return 0
    
    print(f"\nProcessing {file_path}")
    print(f"Found {len(public_types)} public types: {', '.join(public_types)}")
    
    # Get directory
    dir_path = os.path.dirname(file_path)
    
    # Process each public type
    created_files = []
    for i, type_name in enumerate(public_types):
        # Find the position of the next type (for boundary detection)
        next_type_pos = None
        if i < len(public_types) - 1:
            next_type = public_types[i + 1]
            next_pattern = rf'public\s+(?:static\s+)?(?:final\s+)?(?:abstract\s+)?(?:class|interface|enum)\s+{next_type}'
            next_match = re.search(next_pattern, content)
            if next_match:
                next_type_pos = next_match.start()
        
        # Extract this type's content
        type_content = extract_type_content(content, type_name, next_type_pos)
        
        if type_content:
            # Create new file
            new_file_path = os.path.join(dir_path, f"{type_name}.java")
            
            # Build file content
            file_content = package_line + '\n\n'
            if import_block:
                file_content += import_block + '\n\n'
            file_content += type_content + '\n'
            
            # Write new file
            with open(new_file_path, 'w', encoding='utf-8') as f:
                f.write(file_content)
            
            created_files.append(new_file_path)
            print(f"  Created: {new_file_path}")
    
    # Remove the original file since all types have been extracted
    if created_files:
        os.remove(file_path)
        print(f"  Removed original file: {file_path}")
    
    return len(created_files)

def main():
    """Process all Java files that need splitting."""
    base_dir = "/Users/ryankim/Workspace/etc/ryanium-proj/ryan-shopify-connector-java/shopify-spring-sdk/src/main/java/com/shopify/sdk/model"
    
    # List of files to process
    files_to_split = [
        "metafield/MetafieldInput.java",
        "marketing/MarketingActivity.java",
        "marketing/MarketingEvent.java",
        "marketing/MarketingInput.java",
        "marketplace/Marketplace.java",
        "retail/Retail.java",
        "checkout/CheckoutBranding.java",
        "discount/DiscountAutomaticNode.java",
        "discount/DiscountCodeNode.java",
        "discount/DiscountInput.java",
        "discount/DiscountNode.java",
        "discount/DiscountTypes.java",
        "event/Event.java",
        "fulfillment/FulfillmentInput.java",
        "fulfillment/FulfillmentOrder.java",
        "localization/Localization.java",
        "markets/Markets.java",
        "metaobject/Metaobject.java",
        "onlinestore/OnlineStore.java",
        "order/OrderEnums.java",
        "shipping/DeliveryZone.java",
        "shipping/ShippingInput.java",
        "store/StoreProperties.java",
        "webhook/Webhook.java",
    ]
    
    total_created = 0
    
    for file_rel_path in files_to_split:
        file_path = os.path.join(base_dir, file_rel_path)
        if os.path.exists(file_path):
            created = split_java_file(file_path)
            total_created += created
        else:
            print(f"File not found: {file_path}")
    
    print(f"\n\nTotal files created: {total_created}")
    
    # Also check for any other files that might have multiple public types
    print("\n\nChecking for any other files with multiple public types...")
    for root, dirs, files in os.walk(base_dir):
        for file in files:
            if file.endswith('.java'):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                    public_types = extract_public_types(content)
                    if len(public_types) > 1:
                        rel_path = os.path.relpath(file_path, base_dir)
                        if rel_path not in files_to_split:
                            print(f"Found additional file with multiple public types: {rel_path}")
                            created = split_java_file(file_path)
                            total_created += created
                except Exception as e:
                    print(f"Error processing {file_path}: {e}")
    
    print(f"\n\nFinal total files created: {total_created}")

if __name__ == "__main__":
    main()