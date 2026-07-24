# ZamoraMC-Close

ZamoraMC-Close is a lightweight Paper plugin that allows authorized administrators to remotely close the open inventory of a connected player.

## Features

- Works from the server console.
- Works for players with the `zamoramcclose.use` permission.
- Closes any Bukkit/Paper inventory currently open by the target player.
- Supports reloading `config.yml` without restarting the server.
- Sends configurable messages to the command sender and affected player.
- Includes player-name tab completion.
- Uses Bukkit permissions and requires no LuckPerms dependency.

## Command

```text
/zamoramc-close <player>
/zamoramc-close reload
```

## Permission

```text
zamoramcclose.use
```

The permission defaults to server operators. The console can always execute the command.

## Installation

1. Download the plugin JAR from a release.
2. Place it in the server's `plugins` directory.
3. Start or restart the Paper server.
4. Edit `plugins/ZamoraMC-Close/config.yml` if you want to customize the messages.

## Configuration

```yaml
messages:
  no-permission: "&cNo tienes permiso."
  player-not-found: "&cEl jugador no está conectado."
  usage: "&cUso: /zamoramc-close <jugador|reload>"
  reload-success: "&aConfiguración recargada correctamente."
  inventory-closed: "&aInventario cerrado correctamente."
  inventory-closed-target: "&eTu inventario ha sido cerrado."
```

Messages use Bukkit's legacy `&` color-code format.

## Compatibility

The plugin targets stable Bukkit/Paper APIs and is intended for:

- Paper 1.21 through 1.21.11;
- Paper 26.2.

Paper's version numbering changes after the 1.21.x line. Paper 1.21.12 compatibility is [Not verified] because no separate server build was available during validation.

## Build

The project uses Maven and Java 21 bytecode. The build uses JDK 25 because the selected Paper 26.2 API is published for the modern Paper line.

```text
./mvnw clean package
```

On Windows:

```text
mvnw.cmd clean package
```

The compiled artifact is generated at:

```text
target/ZamoraMC-Close-1.0.0.jar
```

## Testing

The unit test suite covers console execution, permission handling, argument validation, player lookup, tab completion, configuration reload, configurable messages, and the invocation of Bukkit's `Player#closeInventory()` API. MockBukkit was evaluated for a stateful open-inventory test, but the available releases tested were not compatible with the selected Paper API 26.2 test classpath, so it is not included as a project dependency. Server startup was also checked with Paper 1.21.11 on JDK 21 and Paper 26.2 on JDK 25. Visual client-side inventory closure was not tested because no live Minecraft client was available.

## License

This project is licensed under the MIT License. See [LICENSE.md](LICENSE.md).
