## QueueWatch for Constantiam

Tiny plugin to help keep zenith bots on Constantiam 24/7.
Optional discord role ping can be set.
Please note theres probably better ways to detect the server change this was just the simplist for me.

### Usage

When the server reboots, lags, or kicks you the zenith bot ends up in the queue server indefinetly. This plugin detects the system chat message which is always sent when you're moved there and disconnects. Reconnecting is left up to the reconnect module built in so make sure its enabled if you want the bot to stay online.
Note: the detected message can be modified manually in the config file if this method of detecting the queue is patched or something
### Commands

```bash
queuewatch <on/off>
```
```bash
queuewatch role <roleId|off>
```

### Building The Plugin

Clone the repo or download the zip.  
Run `./gradlew build`
