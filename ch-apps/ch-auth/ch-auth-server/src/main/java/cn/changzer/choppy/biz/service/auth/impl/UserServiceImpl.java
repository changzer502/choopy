package cn.changzer.choppy.biz.service.auth.impl;

import cn.changzer.choppy.biz.dao.auth.UserMapper;
import cn.changzer.choppy.biz.service.auth.UserService;
import cn.changzer.choppy.dto.auth.UserUpdatePasswordDTO;
import cn.changzer.choppy.entity.auth.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务实现类
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public List<User> findUserByRoleId(Long roleId, String keyword) {
        return null;
    }

    @Override
    public void updatePasswordErrorNumById(Long id) {

    }

    @Override
    public User getByAccount(String account) {
        return null;
    }

    @Override
    public void updateLoginTime(String account) {

    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public boolean reset(List<Long> ids) {
        return false;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public boolean remove(List<Long> ids) {
        return false;
    }

    @Override
    public Boolean updatePassword(UserUpdatePasswordDTO data) {
        return null;
    }

    @Override
    public int resetPassErrorNum(Long id) {
        return 0;
    }
    //@Autowired
    //private UserRoleService userRoleService;
    //
    //@Override
    //public IPage<User> findPage(IPage<User> page, LbqWrapper<User> wrapper) {
    //    return baseMapper.findPage(page, wrapper);
    //}
    //
    //@Override
    //public int resetPassErrorNum(Long id) {
    //    return baseMapper.resetPassErrorNum(id);
    //}
    //
    //@Override
    //public Boolean updatePassword(UserUpdatePasswordDTO data) {
    //    BizAssert.equals(data.getConfirmPassword(), data.getPassword(), "密码与确认密码不一致");
    //
    //    User user = getById(data.getId());
    //    BizAssert.notNull(user, "用户不存在");
    //    String oldPassword = DigestUtils.md5Hex(data.getOldPassword());
    //    BizAssert.equals(user.getPassword(), oldPassword, "旧密码错误");
    //
    //    User build = User.builder().password(data.getPassword()).id(data.getId()).build();
    //    this.updateUser(build);
    //    return true;
    //}
    //
    //@Override
    //public User getByAccount(String account) {
    //    return super.getOne(Wraps.<User>lbQ().eq(User::getAccount, account));
    //}
    //
    //@Override
    //public List<User> findUserByRoleId(Long roleId, String keyword) {
    //    return baseMapper.findUserByRoleId(roleId, keyword);
    //}
    //
    //@Override
    //public void updatePasswordErrorNumById(Long id) {
    //    baseMapper.incrPasswordErrorNumById(id);
    //}
    //
    //@Override
    //public void updateLoginTime(String account) {
    //    baseMapper.updateLastLoginTime(account, LocalDateTime.now());
    //}
    //
    //@Override
    //public User saveUser(User user) {
    //    // 永不过期
    //    user.setPasswordExpireTime(null);
    //
    //    user.setPassword(DigestUtils.md5Hex(user.getPassword()));
    //    user.setPasswordErrorNum(0);
    //    super.save(user);
    //    return user;
    //}
    //
    //@Override
    //public boolean reset(List<Long> ids) {
    //    LocalDateTime passwordExpireTime = null;
    //    String defPassword = "cea87ef1cb2e47570020bf7c014e1074";//pinda123
    //    super.update(Wraps.<User>lbU()
    //            .set(User::getPassword, defPassword)
    //            .set(User::getPasswordErrorNum, 0L)
    //            .set(User::getPasswordErrorLastTime, null)
    //            .set(User::getPasswordExpireTime, passwordExpireTime)
    //            .in(User::getId, ids)
    //    );
    //    return true;
    //}
    //
    //@Override
    //public User updateUser(User user) {
    //    // 永不过期
    //    user.setPasswordExpireTime(null);
    //
    //    if (StrUtil.isNotEmpty(user.getPassword())) {
    //        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
    //    }
    //    super.updateById(user);
    //    return user;
    //}
    //
    //@Override
    //public boolean remove(List<Long> ids) {
    //    userRoleService.remove(Wraps.<UserRole>lbQ()
    //            .in(UserRole::getUserId, ids)
    //    );
    //    return super.removeByIds(ids);
    //}
}