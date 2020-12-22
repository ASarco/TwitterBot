package com.sarcobjects;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import twitter4j.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.*;

public class AmericanListenerTest {

    @Mock
    Twitter twitter;
    @Mock
    Status status;
    @Captor
    ArgumentCaptor<StatusUpdate> statusUpdate;
    @InjectMocks
    AmericanListener americanListener;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void testReply() throws Exception {

        User user = mock(User.class);
        when(user.getScreenName()).thenReturn("SomeName");

        when(status.getInReplyToScreenName()).thenReturn(AmericanListener.MY_USER_NAME);
        when(status.getId()).thenReturn(424242L);
        when(status.getUser()).thenReturn(user);

        americanListener.reply(status, "AA es American Airlines, AR es Aerolíneas Argentinas");

        verify(twitter).updateStatus(statusUpdate.capture());

        assertThat(statusUpdate.getValue())
                .extracting(StatusUpdate::getInReplyToStatusId, StatusUpdate::getStatus)
                .contains(424242L, "AA es American Airlines, AR es Aerolíneas Argentinas @SomeName");
    }

    @Test
    // to test the circular queue
    public void testReply_SevenTimes() throws Exception {

        User user = mock(User.class);
        when(user.getScreenName()).thenReturn("SomeName");

        UserMentionEntity[] userMentionEntities = {};

        when(status.getUserMentionEntities()).thenReturn(userMentionEntities);
        when(status.getInReplyToScreenName()).thenReturn(AmericanListener.MY_USER_NAME);
        when(status.getId()).thenReturn(424242L);
        when(status.getUser()).thenReturn(user);

        americanListener.reply(status, "AA es American Airlines, AR es Aerolíneas Argentinas");
        americanListener.reply(status, "AR es Aerolíneas Argentinas, AA es American Airlines");
        americanListener.reply(status, "American Airlines es AA, Aerolíneas Argentinas es AR");
        americanListener.reply(status, "Aerolíneas Argentinas es AR, American Airlines es AA");
        americanListener.reply(status, "Código de Aerolíneas Argentinas: AR, Código de American Airlines: AA");
        americanListener.reply(status, "Código de American Airlines: AA, Código de Aerolíneas Argentinas: AR");
        americanListener.reply(status, "AA es American Airlines, AR es Aerolíneas Argentinas");

        verify(twitter, times(7)).updateStatus(statusUpdate.capture());

        assertThat(statusUpdate.getAllValues())
                .extracting(StatusUpdate::getInReplyToStatusId, StatusUpdate::getStatus)
                .containsExactly(
                        tuple(424242L, "AA es American Airlines, AR es Aerolíneas Argentinas @SomeName"),
                        tuple(424242L, "AR es Aerolíneas Argentinas, AA es American Airlines @SomeName"),
                        tuple(424242L, "American Airlines es AA, Aerolíneas Argentinas es AR @SomeName"),
                        tuple(424242L, "Aerolíneas Argentinas es AR, American Airlines es AA @SomeName"),
                        tuple(424242L, "Código de Aerolíneas Argentinas: AR, Código de American Airlines: AA @SomeName"),
                        tuple(424242L, "Código de American Airlines: AA, Código de Aerolíneas Argentinas: AR @SomeName"),
                        tuple(424242L, "AA es American Airlines, AR es Aerolíneas Argentinas @SomeName"));
    }
}
