package com.reubenpeeris.wippen.engine;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestContext {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

	public static final MatchRunner matchRunner = context.getBean(MatchRunner.class);
	public static final SetRunner setRunner = context.getBean(SetRunner.class);
	public static final GameRunner gameRunner = context.getBean(GameRunner.class);
	public static final DealRunner dealRunner = context.getBean(DealRunner.class);
	public static final MoveRunner moveRunner = context.getBean(MoveRunner.class);
}
