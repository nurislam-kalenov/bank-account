# Tuum Bank Account Service

# Local infrastructure for development

Code and scripts required to get required applications running in your machine.

## Requirements

* Docker
* Docker Compose

## Staring all required services

Creates all services and sets them up for you to be able to just start an application or run tests.

    docker-compose up -d

## Staring bank-account service

    gradle build
    docker build -t tuumsolutions/bank-service .    
    docker run -p 8080:8080 tuumsolutions/bank-service
    or just press green button in IDE and fly:)

## Task questions:

• Explanation of important choices in your solution

1. 'Command' or 'UseCase' design. Instead of traditional service-method design I decided to use approach from Clean
   Architecture. Each operation is independent and isolated and do only what it promises. Predictable and easy to test.
   Plus there are 2 independent domains that easy to split and move to another module/microservice.
2. Custom response model. Custom error messages (GlobalExceptionHandler). It needs to be flexible and independent from
   Spring Web responses and error handlers.
3. API can be internal and external. There is versioning of API (/v1)
4. Create and update operation runs in one transaction otherwise it will rollback.
5. For the update operation where can be Lost Updates, Dirty Reads, Non-Repeatable Reads, Concurrency Anomalies, I used
   optimistic locking.
6. System send message in universal JSON format since consumers can be non-Java system.
7. 'Return a list of transactions' can be looked inefficient since there is no pagination.

• Estimate on how many transactions can your account application can handle per second on your development machine Used 
tool: *JMeter*. API: POST `v1/transaction`.

| Thread count            |  Loop count   |  Error Rate                 |  Throughput (ms)  |
| ----------------------  |:-------------:| ---------------------------:| -----------------:|
| 1                       | 10000         | 0.00%                       |141                |
| 10 (same account id)    | 10000         | 78.97%(optimistic locking)  | 2.4               |
| 10                      | 10000         | 0.00%                       | 195               |
| 50                      | 100000        | 0.00%                       | 221               |
| 70                      | 1000000       | 5.34%(db connection)        | 219               |

• Describe what do you have to consider to be able to scale applications horizontally.

1. It really depends on many facts. Let's say 2-3 million transactions per day. The system needs to process 2 million
   transactions per day, which is 2,000,000 transactions / 20^5 seconds = 20 transactions per second (TPS). 15-20 TPS is
   not a big number for a Postgres database. Anyway it is obvious that the transaction domain will be loaded more than
   the account module hundreds or even a thousand times. The solution would be to take out the transaction module
   separately and set auto-scale. And we can call it 'Payment executor' service.
2. Postgres database replication. There will be a lot of insert probably we should think about sharding.
3. From my experience with similar system where it were Payment service -> Payment executor -> Payment Service
   Provider (PSP) I would say using MQ is suitable.

## Feedback:

The task was interesting, especially from the point of view of the fact that I only worked with MyBatis in EPAM when I
was doing an internal project. In those days I used xml but this time I decided to use annotations. All in all, I got a
lot of satisfaction from working with MyBatis.