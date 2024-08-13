# compass-test

Compass Coding Challenge

## Description

This project addresses a technical challenge where the goal is to identify potential duplicates in a contact list and
calculate a precision score for each match. The objective is to compare various fields between contacts and determine
their similarity, generating a report that shows potential matches along with their accuracy score.

## Example

### Input:

| Contact ID | First Name | Last Name | Email Address                  | Zip Code | Address              |
|------------|------------|-----------|--------------------------------|----------|----------------------|
| 1001       | C          | F         | mollis.lectus.pede@outlook.net |          | 449-6990 Tellus. Rd. |
| 1002       | C          | French    | mollis.lectus.pede@outlook.net | 39746    | 449-6990 Tellus. Rd. |
| 1003       | Ciara      | F         | non.lacinia.at@zoho.ca         | 39746    |                      |

### Output:

| Contact ID Source | Contact ID Match | Accuracy |
|-------------------|------------------|----------|
| 1001              | 1002             | High     |
| 1001              | 1003             | Low      |

## Features

1. **Duplicate Identification**: The program identifies which contacts are potential duplicates.
2. **Accuracy Scoring**: A score is assigned to each match based on the similarity of the fields compared.
3. **Report Generation**: The report shows potential matches with their accuracy level (e.g., "High" or "Low").
4. **In-Memory Processing**: All processing is done in-memory without using a database.

## How to Run the Project

### Prerequisites

- Java 21
- [Gradle](https://gradle.org/) (to manage dependencies)

### Instructions

1. **Clone the repository:**

   ```bash
   git clone https://github.com/FdHerrera/compass-test.git
   cd compass-test
   ```

2. **Run the tests**

```
   ./gradlew test
```

## Solution Explanation

The solution is designed to be scalable and efficient:

* Field-by-Field Comparison: Relevant fields such as first name, last name, email, zip code, and address are compared.
* Annotation-Based Fields: The fields to be compared are annotated in the Contact class, making it easy to extend the
  functionality.
* Dynamic Scoring System: A dynamic scoring system adjusts the accuracy threshold automatically based on the number of
  fields compared, eliminating the need for manual adjustments.
