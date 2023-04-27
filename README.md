# ProjectLibUpdate

Technology: Maven, Spring MVC, Spring Core, Hibernate and Spring Data JPA, Thymeleaf, PostgreSQL.

Catalog of library


This web apps is as ProjectLib, but implemented on Hibernate and Spring Data JPA. Added functions 
of splitting the list of books into pages, sorting books, the search window for a book by the first letters,
tracking the delay of a book. 
Для запуска через docker compose:
1) Создать файл  docker compose.yml:


```sh
version: '3.7'

services:
  lb:
    depends_on:
      - postgres
    image: vyurkin/projectlibupdate
    environment:
      DB_USERNAME: postgres
      DB_PASSWORD: 9876543219
      DB_URL: jdbc:postgresql://postgres:5432/lbDB
    restart: always
    ports:
      - 8083:8080

  postgres:
    image: postgres:15.2
    restart: always
    environment:
      POSTGRES_PASSWORD: 9876543219
      POSTGRES_USER: postgres
      POSTGRES_DB: lbDB
    ports:
      - 5433:5432
```

2) Запустить сборку образа командой:

 docker compose -p "project-lib" -f "docker-compose.yml" up -d.
 
если образы vyurkin/projectlibupdate и postgres:15.2 отсутствуют, они будут скачаны с DockerHub.


Функционал:
1)	По адресу http://localhost:8083/projectlibupdate/people доступен перечень пользователей библиотеки, главная страница пользователей библиотеки (для примера добавлено несколько пользователей миграцией).
Ссылки на пользователей активные, при выборе переводят на страницу пользователя http://localhost:8083/projectlibupdate/people/1, также на главной странице присутствует активная ссылка на добавление нового пользователя http://localhost:8083/projectlibupdate/people/new.

2)	На странице пользователя приведена информация о пользователе и  список взятых им книг, если пользователь не вернул книгу через более чем 10 дней, цвет шрифта меняется на красный (для примера у пользователя Иванов Иван Иванович две книги, одна из них просрочена).
Также на странице пользователя активные ссылки на редактирование информации о пользователе(http://localhost:8083/projectlibupdate/people/1/edit) и удаление пользователя.

3)	По адресу http://localhost:8083/projectlibupdate/books доступен перечень книг библиотеки, главная страница книг библиотеки (для примера добавлено несколько книг миграцией).
Ссылки на главной странице книг активные, при выборе переводят на страницу книги http://localhost:8083/projectlibupdate/books/1, также на главной странице книги присутствует активная ссылка  на добавление новой книги и страницы поиска книги (http://localhost:8083/projectlibupdate/books/search).

Также список книг на главном экране можно запрашивать постранично. Для этого в адресной строке необходимо ввести два ключа: page и books_per_page. Первый ключ сообщает, какую страницу мы запрашиваем, а второй ключ сообщает, сколько книг должно быть на одной странице.Нумерация страниц стартует с 0. Если в адресной строке не передаются эти ключи, то возвращаются как обычно все книги.
Пример http://localhost:8083/projectlibupdate/books?page=1&books_per_page=3 
На каждую страницу приходится по 3 книги, запрашиваем вторую страницу).

Список книг на главном экране может быть отсортирован по году для этого в адресной строке необходимо ввести ключ sort_by_year. Если он имеет значение true, то выдача будет отсортирована по году. Если в адресной строке не передается этот ключ, то книги возвращаются в обычном порядке.
Пример http://localhost:8083/projectlibupdate/books?sort_by_year=true при таком запросе книги будут отсортированы.

Пагинация и сортировка могут работать одновременно (если передаются сразу три параметра в запросе).

4)	На странице книги приведена информация о книге и кто ее взял, если книга свободна, то из выпадающего списка можно назначить пользователя, который ее возьмет, если книга уже взята, то ее можно освободить при возврате книги.
Также на странице книги присутствуют активные ссылки на редактирование информации о книге (http://localhost:8083/projectlibupdate/books/1/edit) и ее удаление.

5)	На странице поиска книги осуществляется поиск по первым буквам названия книги, при наличии книги с такими буквами выдается книга и кто ее взял, в противном случае выдается сообщение, что книг не найдено. 
