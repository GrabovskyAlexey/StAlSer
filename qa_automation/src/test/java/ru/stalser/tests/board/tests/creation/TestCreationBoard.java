package ru.stalser.tests.board.tests.creation;

import io.qameta.allure.*;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.gb.stalser.api.dto.board.BoardDto;
import ru.stalser.TestBase;
import ru.stalser.framework.autotestobjects.BoardCreateRqDto;
import ru.stalser.framework.helpers.Cache;
import ru.stalser.framework.helpers.DBHelper;
import ru.stalser.framework.helpers.ParseHelper;

import java.time.temporal.ChronoUnit;
import java.util.Map;

import static ru.stalser.framework.helpers.SoftAssertHelper.softly;
import static ru.stalser.framework.steps.rests.StalserRestSteps.doPostToCreateBoard;
import static ru.stalser.framework.testdata.TestUsers.AUTOTEST_USER;

@Epic("Stalser")
@Feature("Доски")
@Story("Создание доски - позитив")
@Tag("Stalser.Board")
public class TestCreationBoard extends TestBase {

    @Test
    @DisplayName("01. Создание юзера c ролью ROLE_USER")        //TODO
    @Description("Регистрация нового юзера c ролью ROLE_USER в Stalser")
    @Severity(SeverityLevel.BLOCKER)
    @Tag("BLOCKER")
    public void testCreateBoard() {

        /* Создание реквеста создания */
        BoardDto boardDto = createDefaultBoardDto();

        /* Создание борды */
        BoardCreateRqDto boardCreateRqDto = doPostToCreateBoard(boardDto);

        /* Проверка записи в таблице boards */
        assertBoardInBD(boardCreateRqDto);

        /* Удаление созданной доски из таблицы boards */
        deleteRawFromBoardsTable(boardDto.getId().toString());
    }

    public static BoardDto createDefaultBoardDto() {

        BoardDto boardDto = new BoardDto();
        boardDto.setBoardName("GrafAutotestTestBoard");
        boardDto.setBoardDesc("!!!Не трогать!!! Тестовая борда для автотестов!!!");
        boardDto.setBoardAlias("ATestBoard");
        boardDto.setIsActive(true);
//        boardDto.setCreator(AUTOTEST_USER);

        return boardDto;
    }

    @Step("Проверка записи в таблице boards")
    public static void assertBoardInBD(BoardCreateRqDto boardCreateRqDto) {

        BoardDto boardDto = boardCreateRqDto.getBoardDto();

        Map<String, String> data = getRawFromBoardsTable(boardDto.getId().toString());

        softly().assertThat(data.get("board_name")).as("board name").isEqualTo(boardDto.getBoardName());
        softly().assertThat(ParseHelper.parseDateTimeFromDBToLocalDateTime(data.get("created_at"))).as("created at")
                .isCloseTo(boardCreateRqDto.getCreationTime(), new TemporalUnitWithinOffset(5, ChronoUnit.SECONDS));
        softly().assertThat(ParseHelper.parseDateTimeFromDBToLocalDateTime(data.get("updated_at"))).as("updated_at")
                .isCloseTo(boardCreateRqDto.getUpdatedTime(), new TemporalUnitWithinOffset(5, ChronoUnit.SECONDS));
        softly().assertThat(data.get("board_alias")).as("board alias").isEqualTo(boardDto.getBoardAlias());
        softly().assertThat(data.get("is_active").equalsIgnoreCase("t")).as("is active").isEqualTo(boardDto.getIsActive());
//        softly().assertThat(Long.parseLong(data.get("creator_id"))).as("creator id").isEqualTo(boardDto.getCreator().getId());    //TODO

    }

    @Step("Получение данных о борде из таблицы boards по id {boardId}")
    public static Map<String, String> getRawFromBoardsTable(String boardId) {

        String sql = String.format("select * from boards where id = %s", boardId);

        DBHelper dbHelper = new DBHelper();
        dbHelper.connectStalser();
        Map<String, String> resultMap = dbHelper.doRequestInToMap(sql, "Делаю запрос в таблицу boards по id = " + boardId);
        dbHelper.disconnect();

        return resultMap;
    }

    public static void deleteRawFromBoardsTable(String boardId) {

        String sql = String.format("delete from boards where id = %s",
                boardId);

        DBHelper dbHelper = new DBHelper();
        dbHelper.connectStalser();
        dbHelper.executeUpdate(sql, "делаю запрос в таблицу boards на удаление доски по id = " + boardId);
        dbHelper.disconnect();
    }
}
