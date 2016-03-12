package dal;

import model.Person;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class RedisImpl implements DataStorageInterface
{
    Jedis jedis = null;
    private static final Logger log = Logger.getLogger( RedisImpl.class.getName() );

    public RedisImpl()
    {
        jedis = new Jedis("localhost");
        log.debug("Server is running: "+jedis.ping());
    }

    @Override
    public ArrayList<Person> read()
    {
//        ArrayList<Person> persons = new ArrayList<Person>();
        Set keys = jedis.keys("*");
        Iterator it = keys.iterator();
        while (it.hasNext())
        {
            Object s = it.next();
            log.debug("read all person - " + s);
            log.debug("current person - " + jedis.get((String) s));
//            persons.add();
        }
        return null;
    }

    @Override
    public void create(Person p)
    {
        log.debug("create person - " + p);
        jedis.set("" + p.getId(), p.toString());
    }

    @Override
    public void update(Person p)
    {
        log.debug("update person - " + p);
        jedis.set("" + p.getId(), p.toString());
    }

    @Override
    public void delete(Person p)
    {
        log.debug("delete person - " + p);
        jedis.del("" + p.getId());
    }
}
