package com.project01.ledgerSystem.service;

import com.project01.ledgerSystem.dto.Detail;
import com.project01.ledgerSystem.mapper.UserMapper;
import com.project01.ledgerSystem.models.User;
import com.project01.ledgerSystem.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

//import static com.project01.ledgerSystem.util.MyBatisUtil.MyBatisUtil;

public class UserServiceImpl implements UserService {

    // 注册
    @Override
    public boolean signIn(String name, String password) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        boolean result = false;
        System.out.println("--------------注册---------------");
        User user = null;
        if (!name.isEmpty() && name != "" && !password.isEmpty() && password != null) {
            user = new User(name, password);
            result = userMapper.signIn(user);
        }
        sqlSession.close();
        return result;
    }

    // 登陆
    public User logIn(String userName, String userPassword) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = null;
        if (!userName.isEmpty() && userPassword != null) {
            user = userMapper.logIn(userName, userPassword);
        }
        sqlSession.close();
        return user;
    }

    // 取款
    public double withdrawMoney(User user, double money) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession(false);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        double result = user.getUserBalance() - money;
        if (money > 0 || userMapper.getBalance(user.getUserId()) >= money) {
            if (userMapper.resetBalance(user.getUserId(), result) &&
                    userMapper.withdrawMoney(user.getUserId(), money)) {
                System.out.println("成功取出" + money + "元,余额：" + result + "元");
                sqlSession.commit();
            } else {
                sqlSession.rollback();
                result = -1;
            }
        } else {
            result = -1;
        }
        sqlSession.close();
        return result;
    }

    // 存款
    public double saveMoney(User user, double money) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession(false);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        double result = user.getUserBalance() + money;
        if (money > 0) {
            if (userMapper.resetBalance(user.getUserId(), result) &&
                    userMapper.saveMoney(user.getUserId(), money)) {
                System.out.println("成功存入" + money + "元,余额：" + result + "元");
                sqlSession.commit();
            } else {
                sqlSession.rollback();
                System.out.println("存款失败，请重试");
                result = -1;
            }
        } else {
            System.out.println("存款失败，请重试");
            result = -1;
        }
        sqlSession.close();
        return result;
    }

    // 查询所有交易明细
    public boolean getInfo(User user) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        List<Detail> details = userMapper.getInfo(user.getUserId());
        if(!details.isEmpty()){
            System.out.println("用户名"+"\t"+"交易类型"+"\t"+"交易金额"+"\t"+"交易时间");
            for(Detail detail : details){
                System.out.println(detail.toString());
            }
        }else{
            System.out.println("暂无记录");
        }
        sqlSession.close();
        return true;
    }
}
