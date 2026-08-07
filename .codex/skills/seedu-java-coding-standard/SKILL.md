---
name: seedu-java-coding-standard
description: Apply the SE-EDU basic and intermediate Java coding standard to Java code in this project. Use when creating, reviewing, refactoring, or formatting project Java source and tests.
---

# SE-EDU Java Coding Standard

## Overview

Apply the SE-EDU [basic and intermediate Java coding standard](https://se-education.org/guides/conventions/java/intermediate.html) to all project Java code. Use the Google Java Style Guide for topics not covered by SE-EDU.

## Required conventions

- Put every class in a lowercase package; use PascalCase nouns for classes and enums.
- Use camelCase for variables and verb-based methods; use SCREAMING_SNAKE_CASE for constants. Name booleans with prefixes such as `is`, `has`, `can`, or `should`, and use plural names for collections.
- Keep names in English, avoid uppercase acronyms inside names, and use descriptive names for variables with wider scope. Test methods may use `featureUnderTest_testScenario_expectedBehavior`.
- Use four spaces (no tabs), K&R braces, spaces around operators and after commas, blank lines between logical units, and a hard line limit of 120 characters (prefer under 110). Wrap continuation lines with eight additional spaces.
- Use braces for every loop and conditional body, including single statements. Keep conditions and bodies on separate lines. Add `// Fallthrough` to intentional switch fallthrough.
- Order imports consistently and import classes explicitly; never use wildcard imports. Attach array brackets to the type (`String[] values`).
- Initialize variables at declaration when practical and keep them in the smallest possible scope. Do not expose class variables publicly except constants or behavior-free data classes.
- Add descriptive English American-spelling Javadoc to every public class and public method, except getters/setters, tests, and overrides whose inherited documentation applies.

**1. Workflow-Based** (best for sequential processes)
- Works well when there are clear step-by-step procedures
- Example: DOCX skill with "Workflow Decision Tree" -> "Reading" -> "Creating" -> "Editing"
- Structure: ## Overview -> ## Workflow Decision Tree -> ## Step 1 -> ## Step 2...

**2. Task-Based** (best for tool collections)
- Works well when the skill offers different operations/capabilities
- Example: PDF skill with "Quick Start" -> "Merge PDFs" -> "Split PDFs" -> "Extract Text"
- Structure: ## Overview -> ## Quick Start -> ## Task Category 1 -> ## Task Category 2...

**3. Reference/Guidelines** (best for standards or specifications)
- Works well for brand guidelines, coding standards, or requirements
- Example: Brand styling with "Brand Guidelines" -> "Colors" -> "Typography" -> "Features"
- Structure: ## Overview -> ## Guidelines -> ## Specifications -> ## Usage...

**4. Capabilities-Based** (best for integrated systems)
- Works well when the skill provides multiple interrelated features
- Example: Product Management with "Core Capabilities" -> numbered capability list
- Structure: ## Overview -> ## Core Capabilities -> ### 1. Feature -> ### 2. Feature...

Patterns can be mixed and matched as needed. Most skills combine patterns (e.g., start with task-based, add workflow for complex operations).

## Review workflow

1. Inspect all changed Java files, including tests.
2. Correct naming, layout, imports, braces, variable scope, and required Javadoc without changing behavior.
3. Check lines longer than 120 characters and intentional fallthrough.
4. Run the project's Java build/tests and the complete UI test plan after code changes.

<!-- No bundled resources are required. -->

<!--

### scripts/
Executable code (Python/Bash/etc.) that can be run directly to perform specific operations.

**Examples from other skills:**
- PDF skill: `fill_fillable_fields.py`, `extract_form_field_info.py` - utilities for PDF manipulation
- DOCX skill: `document.py`, `utilities.py` - Python modules for document processing

**Appropriate for:** Python scripts, shell scripts, or any executable code that performs automation, data processing, or specific operations.

**Note:** Scripts may be executed without loading into context, but can still be read by Codex for patching or environment adjustments.

### references/
Documentation and reference material intended to be loaded into context to inform Codex's process and thinking.

**Examples from other skills:**
- Product management: `communication.md`, `context_building.md` - detailed workflow guides
- BigQuery: API reference documentation and query examples
- Finance: Schema documentation, company policies

**Appropriate for:** In-depth documentation, API references, database schemas, comprehensive guides, or any detailed information that Codex should reference while working.

### assets/
Files not intended to be loaded into context, but rather used within the output Codex produces.

**Examples from other skills:**
- Brand styling: PowerPoint template files (.pptx), logo files
- Frontend builder: HTML/React boilerplate project directories
- Typography: Font files (.ttf, .woff2)

**Appropriate for:** Templates, boilerplate code, document templates, images, icons, fonts, or any files meant to be copied or used in the final output.

---

Not every skill requires all three types of resources. -->
