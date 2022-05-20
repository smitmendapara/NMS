package helper;

import dao.ConnectionPoolIml;
import util.Logger;

import javax.servlet.http.HttpServlet;

public class CommonServlet extends HttpServlet
{
    private static final Logger _logger = new Logger();

    public void init()
    {
        try
        {
            //TODO Add error message in the else part
            if (CommonServletHelper.initializeDatabase())
            {
                _logger.info("database successfully initialized!");

                ConnectionPoolIml connectionPoolIml = new ConnectionPoolIml();

                //TODO what if your connection not created as per your need ?
                if (connectionPoolIml.createPool())
                {
                    _logger.info("connection pool successfully created!");

                    //TODO what if your schedular failts to start
                    if (CommonServletHelper.startScheduler())
                    {
                        _logger.info("scheduler successfully started!");

                        Thread pollingThread = new Thread(new PollingInitializer());

                        pollingThread.start();

                        Thread provisionThread = new Thread(new ParallelDiscovery());

                        provisionThread.start();
                    }
                    else
                    {
                        _logger.warn("scheduler not started.");
                    }
                }
                else
                {
                    _logger.warn("connection is not created.");

                    System.exit(0);
                }
            }
            else
            {
                _logger.warn("database not initialized.");

                System.exit(0);
            }
        }
        catch (Exception exception)
        {
            _logger.error("servlet not init successfully!", exception);
        }
    }
}
