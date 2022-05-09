package action.monitor;

import bean.MonitorBean;

import dao.DAO;

import service.ServiceProvider;

import util.Logger;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

public class Monitor
{
    private int id;

    private String ip;

    private String name;

    private String discoveryUsername;

    private String discoveryPassword;

    private String deviceType;

    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscoveryUsername() {
        return discoveryUsername;
    }

    public void setDiscoveryUsername(String discoveryUsername) {
        this.discoveryUsername = discoveryUsername;
    }

    public String getDiscoveryPassword() {
        return discoveryPassword;
    }

    public void setDiscoveryPassword(String discoveryPassword) {
        this.discoveryPassword = discoveryPassword;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    private List<List<String>> monitorList;

    private MonitorBean bean = null;

    private List<MonitorBean> beanList = null;

    public List<MonitorBean> getBeanList()
    {
        return beanList;
    }

    public void setBeanList(List<MonitorBean> beanList)
    {
        this.beanList = beanList;
    }

    private static final Logger _logger = new Logger();

    public String getMonitorForm()
    {
        DAO dao = new DAO();

        try
        {
            beanList = new ArrayList<>();

            monitorList = dao.getMonitorForm(id);

            if (monitorList != null)
            {
                for (List<String> subList : monitorList)
                {
                    bean = new MonitorBean();

                    bean.setId(Integer.parseInt(subList.get(0)));

                    bean.setIP(subList.get(2));

                    bean.setUsername(subList.get(3));

                    bean.setDevice(subList.get(4));

                    beanList.add(bean);
                }
            }
        }
        catch (Exception exception)
        {
            _logger.warn("monitor data not set into the bean!");
        }

        return "success";
    }

    public String provisionMonitor()
    {
        DAO dao = new DAO();

        ServiceProvider serviceProvider = new ServiceProvider();

        setMonitorData();

        beanList = new ArrayList<>();

        bean = new MonitorBean();

        if (dao.checkIpMonitor(ip, deviceType))
        {
            bean.setFlag(serviceProvider.checkDiscovery(id, ip, discoveryUsername, discoveryPassword, deviceType));
        }
        else
        {
            bean.setFlag(Boolean.FALSE);
        }

        beanList.add(bean);

        return "success";
    }

    public String getMonitorData()
    {
        DAO dao = new DAO();

        try
        {
            beanList = new ArrayList<>();

            monitorList = dao.getMonitorTable();

            if (monitorList != null)
            {
                for (List<String> subList : monitorList)
                {
                    bean = new MonitorBean();

                    bean.setId(Integer.parseInt(subList.get(0)));

                    bean.setName(subList.get(1));

                    bean.setIP(subList.get(2));

                    bean.setDevice(subList.get(4));

                    bean.setStatus(subList.get(5));

                    beanList.add(bean);
                }
            }
        }
        catch (Exception exception)
        {
            _logger.warn("monitor data not set into the bean!");
        }

        return "success";
    }

    public String deleteMonitorData()
    {
        DAO dao = new DAO();

        try
        {
            if (dao.deleteMonitorData(id))
            {
                return "success";
            }
        }
        catch (Exception exception)
        {
            _logger.error("monitor row not deleted...", exception);
        }

        return "success";
    }

    private void setMonitorData()
    {
        DAO dao = new DAO();

        try
        {
            List<HashMap<String, Object>> monitorDetailsList = dao.getMonitorDetails(id);

            for (HashMap<String, Object> monitorDetails : monitorDetailsList)
            {
                ip = monitorDetails.get("IP").toString();

                discoveryUsername = monitorDetails.get("Username").toString();

                discoveryPassword = monitorDetails.get("Password").toString();

                deviceType = monitorDetails.get("Device").toString();
            }
        }
        catch (Exception exception)
        {

        }
    }
}
