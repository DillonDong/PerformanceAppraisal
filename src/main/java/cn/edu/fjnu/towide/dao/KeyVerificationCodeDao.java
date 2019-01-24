package cn.edu.fjnu.towide.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyVerificationCodeDao {
	static final String MAXIMUM_PERIOD="5*60";

	@Select("SELECT COUNT(1) FROM key_verification_code WHERE "
			+ "key_word=#{keyWord} "
			+ "AND "
			+ "verification_code=#{verificationCode} "
			+ "AND "
			+ "UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(generate_time)<"+MAXIMUM_PERIOD)
	boolean checkKeyAndVerificationCode(@Param("keyWord")String keyWord, @Param("verificationCode")String verificationCode);

	@Insert("INSERT INTO key_verification_code (key_word,verification_code)VALUES(#{keyWord},#{verificationCode})")
	boolean addKeyVerificationCode(@Param("keyWord")String keyWord, @Param("verificationCode")String verificationCode);

}
