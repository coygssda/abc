package com.zomato.walletsystem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.zomato.walletsystem.controller.RestControl;
import com.zomato.walletsystem.model.CreateWalletModel;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestRestControl {

	private MockMvc mockMvc;

	@InjectMocks
	private RestControl restControl;
	
	private CreateWalletModel createWalletModel;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(restControl).build();
		createWalletModel=new CreateWalletModel();
	}

	@Test
	public void testRestControl() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/zomato/createWallet"))
		
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("New Wallet Created Successfully"));
	}

}
