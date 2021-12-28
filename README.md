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

## Usage

### Add time to timesheet

Log time against a task:

```
tp time add \
  --task <task id> --date <date> --spent <hours> --remain <hours>
```

Log time using a preset: (missing parameters are taken from configured preset. See above)

```
tp time add \
  --preset my-dev-work --date <date>
```

Accepted date formats:

* `YYYY-MM-DD` or anything parseable by `LocalDate.parse()`
* aliases: (single for now)
  * `today` - means today
* single number - means day of current month
* range of dates
  * `2021-12-10..23` - 10th to 23rd of December 2021
  * `10..23` - 10th to 23rd of current month
