package helpers;

import models.BookData;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class BaseMethods {
    public static Boolean isBookDataFieldsNull(BookData book) {
        assertThat(book.getIsbn(), is(notNullValue()));
        assertThat(book.getTitle(), is(notNullValue()));
        assertThat(book.getSubTitle(), is(notNullValue()));
        assertThat(book.getAuthor(), is(notNullValue()));
        assertThat(book.getPublishDate(), is(notNullValue()));
        assertThat(book.getPublisher(), is(notNullValue()));
        assertThat(book.getPages(), is(notNullValue()));
        assertThat(book.getDescription(), is(notNullValue()));
        assertThat(book.getWebsite(), is(notNullValue()));
        return true;
    }
    public static int getRandomNumber(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }
}
