# Numbers finder

web-service ищущий в 20 разных по составу больших текстовых файлах (каждый должен быть
порядка гб.) полученное на вход число n. Файлы состоят только из чисел, которые разделены между собой
запятой. Результат работы необходимо записать в таблицу БД и вернуть объект Result в вызывающую систему.

+ URL для разова web-service:

    `http://localhost:8080/number/{n}`

    Где n, это число которое надо найти в файлах.

+ Если файл был изменен, удален или добавлен новый надо перезапустить приложение или сделать запрос:
`POST http://localhost:8080/number/reindex` 

+ Пример post запроса в консоли браузера:
```
fetch( 
   '/number/reindex', 
   { 
     method: 'POST', 
     headers: { 'Content-Type': 'application/json' },
     body: JSON.stringify({})
   }
 ).then(result => result.json().then(console.log))
```

+ Папка с файлами находится:
`C:\\projects\\numbersFinder\\files`

    Этот путь задается в application.properties в параметре `app.files.dir`

+ Команда для генерации 20 файлов по 1ГБ каждый:
`gen_test_files.py 20 1073741824`

+ sql для создания базы написан для postgres 12
`C:\projects\numbersFinder\db.sql`
