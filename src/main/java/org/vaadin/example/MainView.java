package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
public class MainView extends VerticalLayout {

    private CustomerService service = CustomerService.getInstance();
    private Grid<Customer> grid = new Grid<>(Customer.class);
    private TextField filterText = new TextField();
    
    private CustomerForm form = new CustomerForm(this);

    public MainView() {

        
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);

        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCustomer(new Customer());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCustomerBtn);

        grid.setColumns("firstName", "lastName", "status");

        HorizontalLayout mainContent = new HorizontalLayout(grid, form);

        add(toolbar, mainContent);

        mainContent.setSizeFull();
        grid.setSizeFull();

        updateList();


        grid.asSingleSelect().addValueChangeListener(event ->
            form.setCustomer(grid.asSingleSelect().getValue()));
    
    }

    protected void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }
}
