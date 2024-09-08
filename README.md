<img src="https://i.imgur.com/88BpRPz.png" style="width: 400px">

Eventiful sets a new standard in Minecraft server event handling, delivering unparalleled event handling performance
that surpasses other Spigot implementations. By expertly reconstructing event pipelines, Eventiful optimizes the
processing of multiple concurrent events, ensuring your server runs smoothly even under heavy loads. We achieve this by
meticulously identifying and eliminating bottlenecks that traditionally hamper performance during thousands of event
cycles, protecting your server’s tick rate and guaranteeing a seamless gameplay experience.

# Getting Started
To use the Eventful API in your plugin, you must declare its implementation as a dependency in your `plugin.yml` file as shown below, as it is also a plugin. This ensures that the implementation has loaded before your plugin.
```yaml
depend: [ Eventiful ]
```
Once you've added the `plugin.yml` dependency, you can import the API using a build tool.
<details>
  <summary>Maven</summary>

  ```xml
  <repositories>
    <repository>
      <id>mc-libs-repo</id>
      <url>https://repo.repsy.io/mvn/mdcline/mc-libs/</url>
    </repository>
  </repositories>

  <dependencies>
    <dependency>
      <groupId>io.github.eventiful</groupId>
      <artifactId>eventiful-api</artifactId>
      <version>1.0.0</version>
      <scope>provided</scope>
    </dependency>
  </dependencies>
  ```
</details>
<details>
  <summary>Gradle</summary>

  ```gradle
  repositories {
      maven { url "https://repo.repsy.io/mvn/mdcline/mc-libs/" }
  }
  
  dependencies {
      compileOnly 'io.github.eventiful:eventiful-api:1.0.0'
  }
  ```
</details>

# Developing with Eventiful
The `EventBus` is responsible for managing event registration, dispatching, and unregistration. You can retrieve the `EventBus` instance by extending the `EventifulPlugin` class or using the [`ServicesManager`](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/plugin/ServicesManager.html) directly.

### Option 1: Extend `EventifulPlugin`
By extending `EventifulPlugin`, you gain easy access to the EventBus:
```java
public class MyPlugin extends EventifulPlugin {
    @Override
    public void onEnable() {
        EventBus eventBus = getEventBus();
        // Now you can register listeners or dispatch events
    }
}
```
### Option 2: Use `ServicesManager` Directly
If you prefer not to extend `EventifulPlugin`, you can retrieve the `EventBus` like this:
```java
public class MyPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        EventBus eventBus = getServer().getServicesManager().load(EventBus.class);
        // Now you can register listeners or dispatch events
    }
}
```
## Registering an `EventListener`
To listen to specific events, you need to register an `EventListener` with the EventBus. The `EventListener` interface allows you to handle events without needing to use the [`EventHandler`](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/EventHandler.html) annotation.
```java
public class PlayerMoveListener implements EventListener<PlayerMoveEvent> {
    @Override
    public void handle(PlayerMoveEvent event) {
        event.getPlayer().sendMessage("You moved!");
    }
}
```
Now, register this listener in your plugin:
```java
EventBus eventBus = getEventBus(); // Or retrieve it using ServicesManager
PlayerMoveListener listener = new PlayerMoveListener();
eventBus.register(PlayerMoveEvent.class, listener);
```
## Registering an `EventListener`: `Cancellable` Events
Eventiful supports [`Cancellable`](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/Cancellable.html) events. When working with cancellable events, you can extend the `CancellableEventListener` class to simplify handling:
```java
public class PlayerMoveListener extends CancellableEventListener<PlayerMoveEvent> {
    @Override
    protected void handleCancellable(PlayerMoveEvent event) {
        event.getPlayer().sendMessage("You moved!");
    }
}
```
If you wish to not have the `CancellableEventListener` ignore cancelled events, override the `isIgnoringCancelled` method to return `false`.
## Unregistering an `EventListener`
To stop listening to events, you can unregister the `EventListener` using the `EventToken` returned during registration.
```java
EventToken token = eventBus.register(PlayerMoveEvent.class, listener);
// Later, when you want to unregister
eventBus.unregister(token);
```
## Dispatching Events
To trigger events programmatically, use the `dispatch` method on the `EventBus`. This will invoke all registered listeners for the given event.
```java
PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
eventBus.dispatch(event);
```
## Notes
- Thread Safety: The `EventBus` guarantees that event listeners are executed in the appropriate thread context. For example, if an asynchronous event is mistakenly dispatched on the main thread, an `EventConcurrencyException` will be thrown. Similarly, if a synchronous event is dispatched on a background thread, the same exception will occur to enforce thread safety.
- Event Priority: You can control the order in which listeners are invoked by overriding the `getPriority` method in your `EventListener`.
<br><br>

For more details and advanced usage, please refer to the API Javadocs.
