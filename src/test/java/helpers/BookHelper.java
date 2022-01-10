package helpers;

import models.BookData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class BookHelper {
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
}
