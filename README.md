# Targetprocess client

Purpose of this project is to have a command line utility
to communicate with Targetprocess API.

## Installation

There is no install script prepared yet.

Compile and create application:

```bash
./gradlew clean build installDist
```

Link the launcher to a place in your PATH:

```bash
ln -s tp/build/install/tp/bin/tp ~/.local/bin/tp
```

## Configuration

Create file `~/.config/tp/tp.yaml`

Minimal content:

```yaml
targetprocess:
  api:
    url: https://<< company >>.tpondemand.com/api
  username: << targetprocess username >>
  token: << your token here >>
```

Presets for `tp time add`:

```yaml
time:
  presets:
    << preset name >>:
      task: << task id >>
      spent: 8
      remain: 0
    ...
```
