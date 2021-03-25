# projects

w notatniku wkleić poniższe komenty sql :

    create user springUser identified by 'springPass';
    create database springdatabase;
    use springDataBase;
    grant all privileges on springdatabase.* to springUser;

nastepnie zapisać plik jako spring.sql

Według wstepnych założeń, baza danych musi działać na serwerze o adresie  jdbc:mysql://localhost:3306/springdatabase oraz musi posiadać użytkownika  springUser,
z uprawnieniami do tego zasobu oraz hasłem  springPass. Plik  spring.sql, dołączony do aplikacji, zawiera niezbędne komendy sql,
które tworzą schemat oraz użytkownika, posiadającego uprawnienia do tego zasobu, z odpowiednim hasłem dostępu. 
W przypadku bazy o innym adresie trzeba zmodyfikować plik application.properties.
Po pomyślnym zainstalowaniu MySQL Community w systemie Windows należy :

    nacisnąć  przycisk ‘start’ ,
    w polu wyszukiwania wpisać słowo ‘workbench.
    system Windows 7 wyświetli program do uruchomienia o nazwie MySQL Workbench 8.0 CE
    po uruchomieniu programu należy wybrać local instance MySQL,
    będąc zalogowany jako root, w środkowym oknie wybieramy ikonę z katalogiem ‘Open a srcipt file in this editor’,
    następnie należy wybrać plik spring.sql 
    plik zostanie wczytany i wyświetlą się cztery komendy SQL. 
    w tym samym oknie, z górnego menu należy nacisnąć znak pioruna jak na rysunku 12. 
    po prawidłowym wykonaniu skryptu, bazę należy wystartować,
    z górnego menu wybrać należy Database a następnie z rozwijanego menu Manage Server Connections
    w oknie managera należy wybrać New, a następnie wypełnić pola Connection Name (dowolna nazwa), Username (springUser) i Password (springPass)
    oraz nacisnąć przycisk Test Connection, w przypadku pozytywnego testu należy przejść do punktu 12, w przypadku niepowodzenia należy zweryfikować
    poprawność wprowadzonej nazwy użytkownika i hasła z danymi prezentowanymi w niniejszym punkcie, powyżej w nawiasach, 
    z górnego menu wybrać należy Database a następnie z rozwijanego menu Connect to Database,
    z rozwijanej listy Stored Connection należy wybrać zapisany w punkcie 11 profil połączenia, a następnie kliknąć przycisk OK, 
    od tej pory baza danych jest gotowa do współpracy z aplikacją.
    
    kompilacja spring-boot maven plugin - goal run 
    kontener Tomcat embedded
