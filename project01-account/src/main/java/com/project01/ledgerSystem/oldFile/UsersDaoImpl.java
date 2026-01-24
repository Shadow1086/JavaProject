//package com.project01.ledgerSystem.mapper;
//
//import com.project01.ledgerSystem.models.User;
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//public class UsersDaoImpl implements UserMapper{
//    SqlSessionFactory sqlSessionFactory;
//
//    // 初始化块在字段声明之后执行
//    {
//        try {
//            String resource = "mybatis-config.xml";
//            InputStream inputStream = Resources.getResourceAsStream(resource);
//            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * 用户注册
//     *
//     * @param user 从界面获取用户对象
//     */
//    @Override
//    public boolean signIn(User user) {
//        SqlSession sqlSession = sqlSessionFactory.openSession(true);
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//
//        Boolean result = userMapper.signIn(user);
//
//        sqlSession.close();
//        return result;
//    }
//
//    /**
//     * 用户的登录
//     *
//     * @param name     用户名
//     * @param password 密码
//     */
//    @Override
//    public User logIn(String name, String password) {
//        SqlSession sqlSession = sqlSessionFactory.openSession(true);
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//
//        User user = userMapper.logIn(name, password);
//        sqlSession.close();
//    }
//
//    /**
//     * 取/存 款
//     */
//    @Override
//    public double withdrawSaveMoney(User user, double money, int funcNum) {
//        SqlSession sqlSession = sqlSessionFactory.openSession(true);
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//
//        boolean success = userMapper.withdrawSaveMoney(user.getUserId(), money, funcNum);
//
//        sqlSession.close();
//    }
//
//    /**
//     * 获取用户详细的收支记录
//     */
//    @Override
//    public String getInfo(User user) {
//        SqlSession sqlSession = sqlSessionFactory.openSession(true);
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//
//        String result = userMapper.getInfo(user.getUserId());
//        return result;
//    }
//
//    /**
//     * 更新用户余额
//     *
//     * @param userId 用户主键ID
//     * @param money
//     */
//    @Override
//    public boolean resetBalance(int userId, double money) {
//        SqlSession sqlSession = sqlSessionFactory.openSession(true);
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//
//        boolean result = userMapper.resetBalance(userId, money);
//        sqlSession.close();
//        return result;
//    }
//
//    /**
//     * 获取用户的余额,返回-1为错误
//     */
//    @Override
//    public double getBalance(int userId) {
//        SqlSession sqlSession = sqlSessionFactory.openSession(true);
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//
//        double balance = userMapper.getBalance(userId);
//        sqlSession.close();
//        return balance;
//    }
//}
