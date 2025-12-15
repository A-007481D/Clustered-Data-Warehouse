# Clustered-Data-Warehouse



## Data Model

```mermaid
classDiagram
    class Deal {
        +String dealUniqueId
        +String fromCurrencyIso
        +String toCurrencyIso
        +LocalDateTime dealTimestamp
        +BigDecimal dealAmount
    }