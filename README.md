   Проект содержит набор микро-сервисов: ContractService, ContractProcessingService и ContractEventService. 

   ContractService принимает запрос на создание договора по протоколу soap, преобразует полученный конверт во
внутреннее представление CreateNewContract и пересылает сообщение в очередь contract.create RabbitMQ, 
после чего возвращает синхронный ответ со статусом RequestIsQueued. Если при отправке в RabbitMQ происходит ошибка 
(например, если RabbitMQ не доступен), то возвращается ответ со статусом Error и человеко-читаемым сообщением
об ошибке в поле ErrorMessage.
   ContractProcessingService слушает очередь contract.create в RabbitMQ. Полученные из очереди сообщения 
записывает в таблицу contract БД PostgreSQL и отправляет в очередь contract.event событие об успешной 
регистрации в виде экземпляра ContractStatus. Перед сохранением выполняется проверка на дубликаты: в случае,
если в БД уже записан договор с аналогичным contract_number или id - в очередь contract.event отправляется
событие со статусом ошибки.
  ContractEventService слушает очередь contract.event в RabbitMQ. Полученные из очереди сообщения транслирует
в тело POST-запроса и отправляет по адресу http:/host:port/status.

Для запуска проекта склонируйте проект и запустите команду docker-compose up.
Для тестирования проекта импртируйте файл ContractService-soapui-project.xml в SoapUI. Запустите мок ContractEventServiceMock и выполните TestRequest
