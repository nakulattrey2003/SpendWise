SpendWise is a user-friendly money management app designed to help you track expenses, set budgets, and gain insights into your spending habits. With intuitive features and a clean interface,
SpendWise empowers you to make smarter financial decisions and achieve your savings goals.

### Real-Life Analogy:

You = Customer
Waiter = Controller
Kitchen = Service
Cookbook = Util
Chefs = Entity
Inventory Manager = Repository
Security Guard = Security
Restaurant Manager = Config
Packaging Guy = DTO

### Folder Responsibilities

| Folder       | Role in Code                                        | Real-Life Role     | Simple                                                                                          |
| ------------ | --------------------------------------------------- | ------------------ | ----------------------------------------------------------------------------------------------- |
| `controller` | Handles HTTP requests (e.g., `/add-expense`)        | Waiter             | Talks to the user (browser/app), takes input, and sends it to the kitchen.                      |
| `service`    | Business logic                                      | Kitchen            | Prepares the actual food (logic) based on the order.                                            |
| `repository` | Talks to the database                               | Inventory Manager  | Gets or saves ingredients (data) from storage (DB).                                             |
| `entity`     | Data model (table blueprint)                        | Chef               | Represents the dish being cooked — what ingredients go in (fields like amount, date, category). |
| `dto`        | Custom objects for sending/receiving data           | Packaging guy      | Packages the food nicely before giving it to the customer.                                      |
| `util`       | Helper code (e.g., date formatter, token generator) | Cookbook           | Reusable tools or helpers used by chefs and kitchen.                                            |
| `security`   | Manages login/auth                                  | Security guard     | Controls who gets in, who can order, and protects the restaurant.                               |
| `config`     | Configuration files                                 | Restaurant manager | Sets up how things should work — timings, rules, menus.                                         |

### Flow

[React UI]
↓
@Controller ← Waiter
↓
@Service ← Kitchen
↓
@Repository ← DB guy
↓
Database
↑
DTO/Entity ↔ Util used anywhere
Security used globally
Config runs during setup



Profile:

[
  {
    "id": "user-001",
    "fullName": "Nakul Attrey",
    "email": "nakul@example.com",
    "password": "abc123",
    "profileImageUrl": "https://picsum.photos/seed/1/100/100",
  },
  {
    "id": "user-002",
    "fullName": "Maximus Meridius",
    "email": "maximus@gladiator.com",
    "password": "sword99",
    "profileImageUrl": "https://picsum.photos/seed/2/100/100",
  },
  {
    "id": "user-003",
    "fullName": "Sarah Connor",
    "email": "sarah@resistance.org",
    "password": "termin8",
    "profileImageUrl": "https://picsum.photos/seed/3/100/100",
  },
  {
    "id": "user-004",
    "fullName": "Bruce Wayne",
    "email": "bruce@wayneenterprises.io",
    "password": "batman",
    "profileImageUrl": "https://picsum.photos/seed/4/100/100",
  }
]
