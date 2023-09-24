package br.ce.wcaquino.taskbackend.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {

	@Mock
	private TaskRepo taskRepo;
	
	@InjectMocks
	private TaskController ctrl;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		Task todo = new Task();
		//todo.setTask("descricao");
		todo.setDueDate(LocalDate.now());
		try {
			ctrl.save(todo);
			Assert.fail("nao devia chegar aqui");
		} catch (Exception e) {
			assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task todo = new Task();
		todo.setTask("descricao");
		//todo.setDueDate(LocalDate.now());
		try {
			ctrl.save(todo);
			Assert.fail("nao devia chegar aqui");
		} catch (Exception e) {
			assertEquals("Fill the due date", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task todo = new Task();
		todo.setTask("descricao");
		todo.setDueDate(LocalDate.of(2010, 01, 01));
		try {
			ctrl.save(todo);
			Assert.fail("nao devia chegar aqui");
		} catch (Exception e) {
			assertEquals("Due date must not be in past", e.getMessage());
		}
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task todo = new Task();
		todo.setTask("descricao");
		todo.setDueDate(LocalDate.now());

		ctrl.save(todo);
		
		Mockito.verify(taskRepo).save(todo);

	}
	
	
}
