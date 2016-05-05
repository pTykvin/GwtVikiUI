call mvn clean package
call mvn install:install-file -Dfile=target\gwt-material-viki-1.0.0-SNAPSHOT.jar -DlocalRepositoryPath=D:\viki_repo -DpomFile=pom.xml