# projects

w notatniku wkleić poniższe komendy sql :

create user user_database identified by 'buildingSalesApp';
create database buildingsales;
use buildingsales;
grant all privileges on buildingsales.* to user_database;

nastepnie zapisać jako plik deploy_sql_buildingSalesApp.sql

Informacje na temat puli połączeń z bazą danych, dostępnej w aplikacji znajduje się w pliku  glassfish-resources.xml

Według wstepnych założeń, baza danych musi działać na serwerze o adresie  jdbc:mysql://localhost:3306/buildingsales oraz musi posiadać użytkownika  user_database, 
z uprawnieniami do tego zasobu oraz hasłem  buildingSalesApp. Plik  deploy_sql_buildingSalesApp.sql, dołączony do aplikacji, 
zawiera niezbędne komendy sql, które tworzą schemat oraz użytkownika, posiadającego uprawnienia do tego zasobu,
z odpowiednim hasłem dostępu. Po pomyślnym zainstalowaniu MySQL Community w systemie Windows 7 należy :
    1. nacisnąć  przycisk ‘start’ ,
    2. w polu wyszukiwania wpisać słowo ‘workbench.
    3. system Windows 7 wyświetli program do uruchomienia o nazwie MySQL Workbench 8.0 CE
    4. po uruchomieniu programu należy wybrać local instance MySQL,
    5. będąc zalogowany jako root, w środkowym oknie wybieramy ikonę z katalogiem ‘Open a srcipt file in this editor’,
    6. następnie należy wybrać plik deploy_sql_buildingSalesApp.sql z nośnika załączonego do projektu końcowego
    7. plik zostanie wczytany i wyświetlą się cztery komendy SQL. 
    8. w tym samym oknie, z górnego menu należy nacisnąć znak pioruna jak na rysunku 12. 
    9. po prawidłowym wykonaniu skryptu, bazę należy wystartować,
    10. z górnego menu wybrać należy Database a następnie z rozwijanego menu Manage Server Connections
    11. w oknie managera należy wybrać New, a następnie wypełnić pola Connection Name (dowolna nazwa), Username (user_database) i 
    Password (buildingSalesApp) oraz nacisnąć przycisk Test Connection, w przypadku pozytywnego testu należy przejść do punktu 12,
    w przypadku niepowodzenia należy zweryfikować poprawność wprowadzonej nazwy użytkownika 
    i hasła z danymi prezentowanymi w niniejszym punkcie, powyżej w nawiasach, 
    12. z górnego menu wybrać należy Database a następnie z rozwijanego menu Connect to Database,
    13. z rozwijanej listy Stored Connection należy wybrać zapisany w punkcie 11 profil połączenia, a następnie kliknąć przycisk OK,
    od tej pory baza danych jest gotowa do współpracy z aplikacją.
    
    kompilacja przy użyciu maven war plugin
    
    Opis umieszczenia i uruchomienia aplikacji BuildingSalesApp na serwerze aplikacyjnym Payara. 
    Jeżeli serwer nie został wystartowany należy wykonać następujące czynności :
    1. w katalogu gdzie znajduje się serwer wejść w podkatalog bin i uruchomić asadmin.bat
    2. w konsoli administracyjnej serwera payara wpisać start-domain
    3. aby zatrzymać serwer należy wpisać stop-domain

Jeżeli serwer aplikacyjny został wystartowany pomyślnie, należy otworzyć przeglądarkę internetową i wpisać następujący 
adres : http://localhost:4848/ . Numer portu 4848 jest domyślnym portem konsoli administracyjnej serwera Payara.
Należy uwierzytelnić się używając loginu i hasła (domyślne admin/admin). 
Aby umieścić aplikację na serwerze aplikacyjnym należy wykonać następujące czynności :
    1. z prawego menu konsoli administracyjnej wybrać Applications,
    2. następnie po wyświetleniu się strony Applications należy kliknąć przycisk Deploy… ,
    3. dalej należy nacisnąć przycisk Wybierz plik i wybrać BuildingSalesApp.war,
    4. po wybraniu pliku należy nacisnąć przycisk OK zlokalizowany po prawej stronie w górnym roku,
    5. pojawi się strona jak na rysunku 13, przy nazwie aplikacji należy wcisnąć Launch.

Następnie w przeglądarce internetowej należy wejść na adres :  http://localhost:8080/buildings/index.xhtml 



