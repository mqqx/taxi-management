# Paths

Path definitions are organized within this folder and will be referenced from the [api.yaml](../api.yaml) entrypoint file.

Paths should follow these conventions:

* paths are always plural (e.g. `/drivers`)
* path separator token `_`
* path parameters represent the singular of a path as well as their field (e.g. `/drivers/{driver-id}` as route and `drivers_driver-id` as filename)
* one file per path
* therefore at most one of each http methods per file
