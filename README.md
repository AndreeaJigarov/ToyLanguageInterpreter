# 🚀 Toy Language Interpreter

A robust **Java application** that simulates a custom-designed programming language. This project showcases advanced software architecture and concurrent execution management.

---

### 🌟 Key Technical Highlights

* **Multithreading & Concurrency** 🧵: Implemented a `fork()` statement managed via a fixed thread pool (`ExecutorService`).
* **Intelligent Memory Management** 🧠: Features a custom **Conservative Garbage Collector** using Java Streams to deallocate unreachable heap addresses.
* **Reactive JavaFX GUI** 💻: A modern interface providing real-time monitoring of the execution stack, heap, and symbol tables.
* **Static Type Safety** 🛡️: Includes a **Type Checker** to validate program logic before execution.

---

### 🏗️ Architectural Overview

The project follows the **Model-View-Controller (MVC)** pattern for high maintainability:

1.  **Model**: Encapsulates the complete `PrgState`.
2.  **Controller**: Manages execution flow and synchronization.
3.  **Repository**: Handles data persistence and logging.

---

### 📂 Branch Navigation Guide
This repository uses branches to demonstrate specific language features and synchronization primitives:

* **`main`**: The stable base version containing core statements (such as `If`, `While`, `Assign`, `Print`) and full GUI support.
* **`exemple_usoare`**: A collection of simple additional statement implementations for basic language logic.
* **`toy_semaphore` / `count_semaphore`**: Implementation of semaphore-based synchronization.
* **`cyclic_barrier` / `countdown_latch`**: Advanced execution barriers and coordination.
* **`lock` / `procedure`**: Mutex locking mechanisms and procedure call support.
