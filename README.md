# Money Transfer Rest API

Money transfer Rest API created in Kotlin using Spock as testing framework. 

The resources are: users, accounts and transfers. 

## Running 

**gradlew run** will start a standalone server on http://localhost:8080

## Testing

**gradlew test** 

## API

The exact use of the API is defines in the tests. 

| Resource | Available Endpoints | Comment                                                      |
| -------- | :-----------------: | ------------------------------------------------------------ |
| user     |       /users        | Users are assumed to be managed by another system. So only queries are possible. |
| account  |      /accounts      | Accounts here are accounts registered by the user.           |
| transfer |     /transfers      | Transfers have a state. Right after creation they are in status PENDING, and only after the back office processes they go to SETTLED. |



## Stack

REST tool: Jersey (<https://jersey.github.io/>)

JSON serialization: Jackson (<https://github.com/FasterXML/jackson>) with the Kotlin module.

Test Framework: Spock (<http://spockframework.org/>)

Persistence Framework: Exposed (<https://github.com/JetBrains/Exposed>)



