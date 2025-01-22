package com.example.progetto.GUI.checkoutform;

import com.example.application.services.DatabaseManager;
import com.example.progetto.GUI.CartView;
import com.example.progetto.GUI.dialogs.AddressChooseDialog;
import com.example.progetto.backend.Address;
import com.example.progetto.backend.Cart;
import com.example.progetto.backend.CartItem;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.SoldProduct;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Flex;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexWrap;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Height;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Position;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@PageTitle("Checkout Form")
@Route("Checkout-form")
public class CheckoutFormView extends Div {
    
	private User currentUser = Current.getCurrentUser();
	
	private DatabaseManager dbManager = new DatabaseManager();
    
    private static final Set<String> countries = new LinkedHashSet<>();
    
    private static Checkbox saveNewAddress = new Checkbox("Salva indirizzo");
    
    private static TextField name;
    private static TextField surname;
    private static EmailField email;
    private static ComboBox<String> countrySelect;
    private static TextArea address;
    private static TextField postalCode;
    private static TextField city;
    private static TextField cardHolder;
    private static TextField cardNumber;
    private static TextField securityCode;
    private static Select<String> expirationMonth;
    private static Select<String> expirationYear;

    static {
        countries.addAll(Arrays.asList("Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola",
                "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia",
                "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize",
                "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Bouvet Island",
                "Brazil", "British Indian Ocean Territory", "British Virgin Islands", "Brunei Darussalam", "Bulgaria",
                "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
                "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands",
                "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Cyprus",
                "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador",
                "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands",
                "Faroe Islands", "Federated States of Micronesia", "Fiji", "Finland", "France", "French Guiana",
                "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana",
                "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea",
                "Guinea-Bissau", "Guyana", "Haiti", "Heard Island and McDonald Islands", "Honduras", "Hong Kong",
                "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Ivory Coast",
                "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
                "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau",
                "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
                "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Moldova", "Monaco", "Mongolia",
                "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands",
                "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue",
                "Norfolk Island", "North Korea", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau",
                "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal",
                "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis",
                "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe",
                "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia",
                "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands",
                "South Korea", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname",
                "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic",
                "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago",
                "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine",
                "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands",
                "United States Virgin Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City State", "Venezuela",
                "Vietnam", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zaire", "Zambia",
                "Zimbabwe"));
    }

    public CheckoutFormView() {
    	addClassNames("checkout-form-view");
        addClassNames(Display.FLEX, FlexDirection.COLUMN, Height.FULL);

        Main content = new Main();
        content.addClassNames(Display.GRID, Gap.XLARGE, AlignItems.START, JustifyContent.START, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
        
        content.getStyle().set("max-width", "none");
        content.getStyle().set("margin-left", "90px"); // Assicura che il contenitore sia spostato a sinistra

        content.add(createCheckoutForm());
        content.add(createAside());
        add(content);
    }

    private Component createCheckoutForm() {
        Section checkoutForm = new Section();
        checkoutForm.addClassNames(Display.FLEX, FlexDirection.COLUMN, Flex.GROW);

        H2 header = new H2("Checkout");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph note = new Paragraph("Tutti i campi sono obbligatori (se non diversamente specificato)");
        note.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        checkoutForm.add(header, note);

        checkoutForm.add(createPersonalDetailsSection());
        checkoutForm.add(createShippingAddressSection());
        checkoutForm.add(createPaymentInformationSection());
        checkoutForm.add(new Hr());
        checkoutForm.add(createFooter());

        return checkoutForm;
    }

    private Section createPersonalDetailsSection() {
    	Section personalDetails = new Section();
        personalDetails.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);

        Paragraph stepOne = new Paragraph("Checkout 1/3");
        stepOne.addClassNames(Margin.NONE, FontSize.SMALL, TextColor.SECONDARY);

        H3 header = new H3("Dettagli personali");
        header.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.SMALL, FontSize.XXLARGE);

        name = new TextField("Nome");
        name.setRequiredIndicatorVisible(true);
        name.setPattern("[\\p{L} \\-]+");
        name.addClassNames(Margin.Bottom.SMALL);
        
        surname = new TextField("Cognome");
        surname.setRequiredIndicatorVisible(true);
        surname.setPattern("[\\p{L} \\-]+");
        surname.addClassNames(Margin.Bottom.SMALL);

        email = new EmailField("Email");
        email.setRequiredIndicatorVisible(true);
        email.addClassNames(Margin.Bottom.SMALL);
        if (currentUser != null) {
        	name.setValue(currentUser.getName());
        	surname.setValue(currentUser.getSurname());
        	email.setValue(currentUser.getEmail());
        }

        personalDetails.add(stepOne, header, name, surname, email);
        return personalDetails;
    }

    private Section createShippingAddressSection() {
        Section shippingDetails = new Section();
        shippingDetails.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);

        Paragraph stepTwo = new Paragraph("Checkout 2/3");
        stepTwo.addClassNames(Margin.NONE, FontSize.SMALL, TextColor.SECONDARY);

        H3 header = new H3("Indirizzo di spedizione");
        header.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.SMALL, FontSize.XXLARGE);

        countrySelect = new ComboBox<>("Paese");
        countrySelect.setRequiredIndicatorVisible(true);
        countrySelect.addClassNames(Margin.Bottom.SMALL);
        countrySelect.setItems(countries);
        
        address = new TextArea("Indirizzo");
        address.setMaxLength(200);
        address.setRequiredIndicatorVisible(true);
        address.addClassNames(Margin.Bottom.SMALL);

        Div subSection = new Div();
        subSection.addClassNames(Display.FLEX, FlexWrap.WRAP, Gap.MEDIUM);

        postalCode = new TextField("Codice postale");
        postalCode.setRequiredIndicatorVisible(true);
        postalCode.setPattern("[0-9]{5}"); 
        postalCode.setMaxLength(5);
        postalCode.addClassNames(Margin.Bottom.SMALL);
        postalCode.setWidth("240px");
        
        city = new TextField("Città");
        city.setRequiredIndicatorVisible(true);
        city.addClassNames(Flex.GROW, Margin.Bottom.SMALL);

        try {
            if (currentUser != null) {
                var addresses = dbManager.getAddressesByUserId(currentUser.getId());
                
                // Verifica se ci sono indirizzi
                if (addresses != null && !addresses.isEmpty()) {
                    var firstAddress = addresses.getFirst();
                    countrySelect.setValue(firstAddress.getCountry());
                    address.setValue(firstAddress.getAddress());
                    postalCode.setValue(firstAddress.getCap());
                    city.setValue(firstAddress.getCity());
                } else {
                    countrySelect.setValue("Italy");
                    address.setValue("");
                    postalCode.setValue(" ");
                    city.setValue("");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gestisci l'eccezione SQL
        }
        subSection.add(postalCode, city);

        Button changeAddress = new Button("Scegli un altro indirizzo", event ->{
        	AddressChooseDialog acd;
			try {
				acd = new AddressChooseDialog(dbManager.getAddressesByUserId(currentUser.getId()), selectedAddress -> {
					countrySelect.setValue(selectedAddress.getCountry());
	                address.setValue(selectedAddress.getAddress());
	                postalCode.setValue(selectedAddress.getCap());
	                city.setValue(selectedAddress.getCity());
                });
				acd.open();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        changeAddress.getStyle().set("cursor", "pointer");
        
        saveNewAddress.addClassNames(Margin.Top.SMALL);
        saveNewAddress.getStyle().set("cursor", "pointer");

        shippingDetails.add(stepTwo, header, countrySelect, address, subSection, changeAddress, saveNewAddress);
        return shippingDetails;
    }

    private Component createPaymentInformationSection() {
        Section paymentInfo = new Section();
        paymentInfo.addClassNames(Display.FLEX, FlexDirection.COLUMN, Margin.Bottom.XLARGE, Margin.Top.MEDIUM);

        Paragraph stepThree = new Paragraph("Checkout 3/3");
        stepThree.addClassNames(Margin.NONE, FontSize.SMALL, TextColor.SECONDARY);

        H3 header = new H3("Pagamento");
        header.addClassNames(Margin.Bottom.MEDIUM, Margin.Top.SMALL, FontSize.XXLARGE);

        cardHolder = new TextField("Nome del titolare");
        cardHolder.setRequiredIndicatorVisible(true);
        cardHolder.setPattern("[\\p{L} \\-]+");
        cardHolder.addClassNames(Margin.Bottom.SMALL);

        Div subSectionOne = new Div();
        subSectionOne.addClassNames(Display.FLEX, FlexWrap.WRAP, Gap.MEDIUM);

        cardNumber = new TextField("Numero carta");
        cardNumber.setRequiredIndicatorVisible(true);
        cardNumber.setPattern("[\\d ]{19}");  // 19 perchè aggiunge gli spazi
        cardNumber.addClassNames(Margin.Bottom.SMALL);
        cardNumber.setWidth("240px");
        
        // Listener per gestire la formattazione
        cardNumber.addValueChangeListener(event -> {
            String value = event.getValue();
            value = value.replaceAll("(\\d{4})(?=\\d)", "$1 "); // Aggiunge uno spazio ogni 4 numeri
            cardNumber.setValue(value);  // Imposta il valore formattato
        });

        securityCode = new TextField("Codice di sicurezza");
        securityCode.setRequiredIndicatorVisible(true);
        securityCode.setPattern("[0-9]{3}");
        securityCode.addClassNames(Flex.GROW, Margin.Bottom.SMALL);

        subSectionOne.add(cardNumber, securityCode);

        Div subSectionTwo = new Div();
        subSectionTwo.addClassNames(Display.FLEX, FlexWrap.WRAP, Gap.MEDIUM);

        expirationMonth = new Select<>();
        expirationMonth.setLabel("Mese scadenza");
        expirationMonth.setRequiredIndicatorVisible(true);
        expirationMonth.setItems("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        expirationMonth.setWidth("240px");

        expirationYear = new Select<>();
        expirationYear.setLabel("Anno scadenza");
        expirationYear.setRequiredIndicatorVisible(true);
        expirationYear.setItems("25", "26", "27", "28", "29");
        expirationYear.addClassNames(Flex.GROW, Margin.Bottom.SMALL);

        subSectionTwo.add(expirationMonth, expirationYear);

        paymentInfo.add(stepThree, header, cardHolder, subSectionOne, subSectionTwo);
        return paymentInfo;
    }

    private Footer createFooter() {
        Footer footer = new Footer();
        footer.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.BETWEEN, Margin.Vertical.MEDIUM);

        Button cancel = new Button("Annulla ordine");
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.getStyle().set("cursor", "pointer");
        cancel.setWidth("240px");
        
        cancel.addClickListener(event -> {
        	Cart.removeAll();
        	getUI().ifPresent(ui -> { ui.navigate("");});
        });

        Button pay = new Button("Paga", new Icon(VaadinIcon.LOCK));
        pay.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        pay.getStyle().set("cursor", "pointer");
        pay.setWidth("240px");
        pay.getStyle().set("margin-left", "20px");

        pay.addClickListener(event -> {
        	if (saveNewAddress.getValue()) {
	        	try {
	        		boolean isAddressDifferent = false;
					
					if (dbManager.getAddressesByUserId(currentUser.getId()).isEmpty()) {
						isAddressDifferent = true;
					} else {
						for (Address addr : dbManager.getAddressesByUserId(currentUser.getId())) {
							isAddressDifferent = !addr.getCountry().equals(countrySelect.getValue()) ||
			                        !addr.getAddress().equals(address.getValue()) ||
			                        addr.getCap() != postalCode.getValue() ||
			                        !addr.getCity().equals(city.getValue());
							if (!isAddressDifferent ) break;
						} 
					}
					
					if (isAddressDifferent) {
						 Address newAddress = new Address();
			             newAddress.setIDuser(currentUser.getId());
			             newAddress.setCountry(countrySelect.getValue());
			             newAddress.setAddress(address.getValue());
			             newAddress.setCap(postalCode.getValue());
			             newAddress.setCity(city.getValue());
			             dbManager.saveAddress(newAddress, currentUser.getId());
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}

        	if (!validateForm()) {
				Notification.show("Per favore compila tutti i campi obbligatori correttamente.", 3000, Notification.Position.MIDDLE);
        	}else {
        		ArrayList<SoldProduct> soldProducts = new ArrayList<>();
        		
        		for(CartItem item : Cart.getCartItems()) {
        			for(int i = 0; i<item.getQuantity();i++) {
        				SoldProduct sp = new SoldProduct();
        				sp.setModel(item.getProduct().getModel().getName());
        				sp.setSize(item.getProduct().getSize());
        				soldProducts.add(sp);
        			}
        			try {
						dbManager.deleteProductsByIdAndQuantity(item.getProduct().getId(), item.getQuantity());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		try {
        			
        			dbManager.saveOrder(Current.getCurrentUser().getId(), CartView.getTotal(), soldProducts);				
					getUI().ifPresent(ui -> { ui.navigate("Riepilogo-ordine");});
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}        		
        	}			
        });
        
        footer.add(cancel, pay);
        return footer;
    }

    private Aside createAside() {
        Aside aside = new Aside();
        aside.addClassNames(Background.CONTRAST_5, BoxSizing.BORDER, Padding.LARGE, BorderRadius.LARGE, Position.STICKY);
        aside.getStyle().set("width", "150%"); // Modifica la larghezza
        
        Header headerSection = new Header();
        headerSection.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.BETWEEN, Margin.Bottom.MEDIUM);
        
        H3 header = new H3("Ordine");
        header.addClassNames(Margin.NONE);
        
        Button edit = new Button("Torna al carrello");
        edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        edit.getStyle().set("cursor", "pointer");
        edit.addClickListener(event -> {
            getUI().ifPresent(ui -> {
                ui.navigate("Carrello");
            });
        });
        
        headerSection.add(header, edit);

        UnorderedList ul = new UnorderedList();
        ul.addClassNames(ListStyleType.NONE, Margin.NONE, Padding.NONE, Display.FLEX, FlexDirection.COLUMN, Gap.MEDIUM);

        ul.add(CartView.getCartItems());

     // Ottieni il totale dinamico e visualizzalo
        DecimalFormat df = new DecimalFormat("0.00");
        float total = CartView.getTotal();

        Span totalPrice = new Span("Totale: " + df.format(total) + " €");
        totalPrice.addClassNames(FontSize.LARGE, TextColor.PRIMARY);

        aside.add(headerSection, ul, totalPrice);
        
        return aside;
    }
    
    private boolean validateForm() {
    	if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || countrySelect.isEmpty() || address.isEmpty() ||
    		postalCode.isEmpty() || city.isEmpty() || cardHolder.isEmpty() || cardNumber.isEmpty() || securityCode.isEmpty() ||
    		expirationMonth.isEmpty() || expirationYear.isEmpty()) {
    		return false;
    	}

    	if (!email.getValue().contains(" ")&&!email.isInvalid() && !cardNumber.isInvalid() && !securityCode.isInvalid() && !postalCode.isInvalid()&& !cardHolder.isInvalid()) {
    		return true; 
    	}

    	return false; // Campi non validi
    }
    
    public static String getName() {
    	return name.getValue();
    }
    
    public static String getSurname() {
    	return surname.getValue();
    }
    
    public static String getEmail() {
    	return email.getValue();
    }
    
    public static String getCountry() {
    	return countrySelect.getValue();
    }
    
    public static String getAddress() {
    	return address.getValue();
    }
    
    public static String getPostalCode() {
    	return postalCode.getValue();
    }
    
    public static String getCity() {
    	return city.getValue();
    }
}