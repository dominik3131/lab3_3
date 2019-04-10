package lab3_3;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.iis.mto.time.FakeClock;
import edu.iis.mto.time.Order;
import edu.iis.mto.time.Order.State;
import edu.iis.mto.time.OrderExpiredException;

public class OrderTests {

    private Order order;
    private FakeClock fakeClock;
    @Rule
    public ExpectedException exceptionRule;

    @Before
    public void setUp() {
        fakeClock = new FakeClock();
        order = new Order(fakeClock);
        exceptionRule = ExpectedException.none();
    }

    @Test(expected = OrderExpiredException.class)
    public void shouldThrowExceptionOnExpiredOrder() {
        fakeClock.addDateToReturn(2019, 1, 2, 2, 2);
        fakeClock.addDateToReturn(2019, 1, 4, 2, 2);
        order.submit();
        order.confirm();
    }

    @Test
    public void shouldSubmitOnNotExpiredOrder() {
        fakeClock.addDateToReturn(2019, 1, 2, 2, 2);
        fakeClock.addDateToReturn(2019, 1, 2, 3, 2);
        order.submit();
        order.confirm();
        assertThat(order.getOrderState(), is(not(equalTo(State.CONFIRMED))));
    }

    @Test
    public void shouldSubmitOnOrderWith24H() {
        fakeClock.addDateToReturn(2019, 1, 2, 2, 2);
        fakeClock.addDateToReturn(2019, 1, 3, 2, 2);
        order.submit();
        order.confirm();
        assertThat(order.getOrderState(), is(not(equalTo(State.CONFIRMED))));
    }

}
