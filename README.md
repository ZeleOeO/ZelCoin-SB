# ZelCoin SB

A Spring Boot version of my core java NoobChain implementation [ZelCoin](https://github.com/ZeleOeO/ZelCoin)

## Technologies

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1-green?logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue?logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Containerized--db-blue?logo=docker&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15%2B-blue?logo=postgresql&logoColor=white)

## Prerequisites

- Java 17+
- Docker
- **Optional** - API Client (Postman, Yaak, Insominia etc.). You can always use curl
- **Optional** - Database Management Tool (Dbeaver, Navicat, DataGrip etc.) I think some IDE's come inbuilt with them
- **Optional** - An IDE such as IntelliJ, VSCode, Netbeans

## Installation (Will work on both powershell and bash/zsh terminals)

1. Clone the repository:
   ```bash
   git clone https://github.com/ZeleOeO/ZelCoin-SB
   ```
2. Navigate to the project directory:
   ```bash
   cd ZelCoin-SB
   ```   
3. Open docker if not already running (spring-boot-docker-compose is installed so no need for docker-compose)
4. Run with
   ```shell
   mvn spring-boot:run
   ```
5. Optionally, you can run the `src/ZelCoinSbApplication` file on your IDE

## Usage

- Database
    - To connect and check the database, it is hosted on `jdbc:postgresql://localhost:5432/zelcoin`
    - username: `user`
    - password: `password`
- Application
    - port : `8080`
- API Endpoints
    - You can find the API Endpoints at [API-DOC](http://localhost:8080/swagger-ui/index.html)
    - It's a swagger-ui url, nothing extra needed to run it

## Tests

I don't have any implemented tests, but still... when I implement... you can do the following
<br>
For testing, run:

```shell
mvn test
```

## Usage

Test the endpoints either using curl or an api client of your choice.

You can also use the [Swagger UI](http://localhost:8080/swagger-ui/index.html) to test endpoints.

## Steps to Contribute

Contributions are more than welcome, I'm still working on it though, so... keep that in mind or something.

1. Open an issue first so I can like keep track, but if that's too much stress that's fine too
2. Fork the Repository
3. Clone your fork
4. Create a new branch
   ```bash
   git checkout -b your-branch-name
   ```
5. Make your change
6. Commit your change, please
   use [Conventional Commits](https://gist.github.com/qoomon/5dfcdf8eec66a051ecd85625518cfd13) if you can, I didn't
   really use it here tbh
7. Push your change
8. Make a pull request and reference your issue <br>
   Please stick to conventional methods of programming java and springboot applications, don't mess up my already
   spaghetti code

### _**TODO**_

- [ ] Add Block Handling
- [ ] Allow for Block to handle more than one transaction
- [ ] Allow for Manual Block Mining

### _**Notes**_

There are some changes from the [ZelCoin](https://github.com/ZeleOeO/ZelCoin) project <br>
Like:

- Ledger stores transactions as a list and not a hashmap
- Wallet handles transactions like debit, credit and sending money in general (was moved from ledger class)

## License

[MIT](LICENSE)