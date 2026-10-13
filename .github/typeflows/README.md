# Workflows

```mermaid
flowchart LR
    push(["📤 push"])
    schedule(["⏰ schedule"])
    workflowdispatch(["👤 workflow_dispatch"])
    buildyml["Build"]
    updatedependenciesyml["Update Dependencies"]
    push -->|"branches(only: 1)"|buildyml
    schedule -->|"0 08 * * 1"|updatedependenciesyml
    workflowdispatch --> updatedependenciesyml
```

## Workflows

- [Build](./build/)
- [Update Dependencies](./update-dependencies/)