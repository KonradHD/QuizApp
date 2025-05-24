# QuizApp

## 📚 Nazwa projektu: QuizMaster
Aplikacja webowa do tworzenia, edycji i rozwiązywania quizów, z uwierzytelnianiem użytkowników i systemem ról (USER, ADMIN).


## 🎯 Cel projektu:
Celem aplikacji jest umożliwienie administratorom tworzenia i zarządzania pytaniami quizowymi oraz pozwolenie użytkownikom na ich rozwiązywanie. System pozwala także na rejestrację/logowanie użytkowników i ocenę ich wyników.


## 🧰 Technologie użyte w projekcie:
Spring Boot – główny framework aplikacji
Spring MVC – obsługa żądań HTTP
Spring Security – uwierzytelnianie i autoryzacja
Thymeleaf – generowanie szablonów HTML
Lombok – automatyzacja getterów/setterów i konstruktorów
Maven – zarządzanie zależnościami
HTML/CSS (Bootstrap) – interfejs użytkownika


##👤 System ról:
ROLE_USER: użytkownik może rozwiązywać quizy
ROLE_ADMIN: użytkownik z tą rolą może dodawać, edytować i usuwać pytania


## 🔐 Autoryzacja i rejestracja:
Strony logowania (/login) i rejestracji (/register)
Automatyczne logowanie po rejestracji użytkownika
Weryfikacja ról za pomocą Spring Security


## 📄 Obsługa quizów:
Przegląd listy pytań quizowych na stronie /home
Rozwiązywanie quizu i otrzymanie wyników (/submitQuiz)
Wyświetlanie wyniku użytkownika na stronie /result


## 🔐 Bezpieczeństwo:
Użycie AuthenticationManager i SecurityContextHolder do zarządzania sesją użytkownika
Dostęp do operacji modyfikujących pytania ograniczony tylko do ROLE_ADMIN


## 🔄 Możliwe rozszerzenia:
Baza wyników użytkowników i ranking
Kategorie pytań i filtrowanie quizu
Obsługa wielu quizów
REST API dla aplikacji mobilnej
Wysyłanie wyników e-mailem
