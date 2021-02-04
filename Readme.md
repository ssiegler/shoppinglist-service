# Shopping List Service

A simple shopping list service that allows a user to manage a list of
items they want to buy during their next time doing their groceries.

This project uses gradle as its build tool.
All build commands can be issued using the gradle wrapper.
The following command should work from the project root directory:

```shell
./gradlew build
```

If the above fails in Windows environment, try this instead:

```cmd
gradlew.bat build
```

When the build succeeds, it should place the WAR in the `build/libs` directory. 

## Requirements

The service should allow for the following features:

- [ ] Insert an item
  
  Should generate a unique ID for later reference
  
- [ ] Change an item
  
- [ ] Delete an item
  
- [ ] Get a list of all items

- [x] The project should build a WAR file, deployable on a web container.
  
  The Spring Initializr was used to setup gradle and add a ServletInitializer. 

## Out of Scope

The following aspects are considered out of scope for this demo:

- The service does not offer a UI.
- The shopping list items do not need to survive a service restart.
- Do not bother about multi-user or authentication scenarios. 
- The API does not need to be secured.
 
