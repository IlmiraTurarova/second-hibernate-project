# second-hibernate-project
В проекте были созданы все необходимые энтити классы и замаплены на таблицы схемы movie.<br>
Создан метод, который умеет создавать нового покупателя (таблица customer) со всеми зависимыми полями. <br>
Создан транзакционный метод, который описывает событие «покупатель пошел и вернул ранее арендованный фильм». <br>
Создан транзакционный метод, который описывает событие «покупатель сходил в магазин (store) и арендовал (rental) там инвентарь (inventory). <br>
Создан транзакционный метод, который описывает событие «сняли новый фильм, и он стал доступен для аренды». 

<h3>Предложения по улучшению</h3>
1. Добавить foreign key в таблицу film_text на поле film_id таблицы film <br>
2. В поле special_features таблицы film слишком много информации. Можно было сделать связь ManyToMany и хранить связи в отдельной таблице. В каждой строке была бы особенность, характерная для фильма. Тогда можно было бы фильтровать по ним, сортировать.<br>
3. Поле rating таблицы film содержит enum. Можно было бы список значений сделать проще (без знака подчеркивания), чтобы не прописывать внутри каждого enum значение. В итоге брать сам enum, а не писать под него конвертер. 
