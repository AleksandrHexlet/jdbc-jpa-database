package ru.itmo.database.jpa.coursePaper_4;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ru.itmo.database.entity.Course;
import ru.itmo.database.jpa.dao.BookDao;
import ru.itmo.database.jpa.dao.LibraryUserDAO;
import ru.itmo.database.jpa.entity.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static ru.itmo.database.jdbc.Settings.*;
import static ru.itmo.database.jpa.specification.Specifications.BookSpecifications.*;

public class Main {
    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("climber");
             EntityManager manager = emf.createEntityManager()) {


            List<Climber> groupClimber = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTimeStart = LocalDateTime.of(2023, Month.SEPTEMBER, 25, 14, 35);
            LocalDateTime dateTimeEnd = LocalDateTime.of(2023, Month.OCTOBER, 15, 12, 30);
            String groupStart = dateTimeStart.format(formatter); // "2023-09-25 14:30"
            String groupEnd = dateTimeEnd.format(formatter); // "2023-10-15 12:30"
            Mountain Elbrus = new Mountain("Elbrus", "Russia", 5678);

            Climber climber = new Climber("Ivan", "Moscow");

            Climber climber1 = new Climber("Sergey", "Moscow");
            Climber climber2 = new Climber("Igor", "SPB");

            groupClimber.add(climber);
            groupClimber.add(climber1);
            ClimbingGroup climbingGroup = new ClimbingGroup(groupClimber,true,Elbrus,dateTimeStart,dateTimeEnd);



            manager.getTransaction().begin();
            manager.persist(climbingGroup);
            manager.persist(Elbrus);
            manager.getTransaction().commit();

            System.out.println(manager.find(ClimbingGroup.class, climbingGroup.getId()).getClimbers().get(0));
            System.out.println(manager.find(Mountain.class, Elbrus.getId()).getName());

            groupClimber.add(climber2);

            manager.getTransaction().begin();
            manager.merge(groupClimber);
            manager.getTransaction().commit();

            System.out.println(manager.find(ClimbingGroup.class, climbingGroup).getClimbers().get(2));

            class StatementAndPreparedExamples {
                private static void loadDriver() {
                    try {
                        Class.forName("org.postgresql.Driver");
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("Driver is not found");
                    }
                }

                public static HashSet<String> selectByCondition() {
                    // SQL запросы:
// получить название гор, на которые уже состоялись восхождения
                    String selectSQLgroupIsUp = "SELECT DISTINCT mountain.name FROM ClimbingGroup cg JOIN cg.mountain mountain WHERE cg.endTime IS NOT NULL";

// получить название гор, на которые не было ни одного восхождения
                    String selectSQLgroupNotUp = "SELECT mountain.name FROM Mountain mountain WHERE NOT EXISTS (SELECT 1 FROM ClimbingGroup cg WHERE cg.mountain = mountain)";

// получить имена альпинистов, которые ходили в поход на гору с определенным названием
                    String selectSQLgroupIsUpSpecificMountain = " SELECT climber.fullName FROM Climber climber JOIN .climbingGroups cg JOIN cg.mountain mountain WHERE mountain.name = :mountainName";

// получить названия и высоту гор, восхождение на которые планируется в определенный интервал времени
                    String selectSQLNameAndHeightMountain = "SELECT mountain.name, mountain.height FROM ClimbingGroup cg JOIN cg.mountain mountain WHERE cg.startTime BETWEEN :start AND :end";

// получить имена альпинистов, которые не записались в новые (время начала похода в будущем) группы
                    String selectSQLClimberName = "SELECT climber.fullName FROM Climber climber WHERE NOT EXISTS (SELECT 1 FROM ClimbingGroup cg WHERE cg.isRecruiting = true AND cg.startTime > CURRENT_TIMESTAMP AND climber MEMBER OF cg.climber)";




                    loadDriver();
                    try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PWD)) {
                        try (PreparedStatement statement = connection.prepareStatement(selectSQLgroupIsUp)) {
//                        statement.setInt(1, maxPrice);
//                        statement.setDouble(2, maxDuration);
                            HashSet<String> resultHashSet = new HashSet<>();
                            try (ResultSet resultSet = statement.executeQuery()) {
                                while (resultSet.next()) {
                                    String result = (
                                            resultSet.getString("name")
                                    );
                                    StringBuffer buffer = new StringBuffer();
                                    resultHashSet.add(result);
                                }
                            }
                            if (resultHashSet.isEmpty()) return null;
                            return resultHashSet;
                        }
                    } catch (SQLException throwables) {
                        System.out.println("Не удалось выполнить запрос: " + throwables.getSQLState() + ", " + throwables.getMessage());
                        return null;
                    }
                }


            }

        }

    }
}

