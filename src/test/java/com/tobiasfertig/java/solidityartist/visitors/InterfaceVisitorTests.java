package com.tobiasfertig.java.solidityartist.visitors;

import com.tobiasfertig.java.solidityartist.elements.comments.NatSpecElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.FunctionTypeElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.MappingElement;
import com.tobiasfertig.java.solidityartist.elements.events.EventElement;
import com.tobiasfertig.java.solidityartist.elements.files.InterfaceElement;
import com.tobiasfertig.java.solidityartist.elements.functions.CodeElement;
import com.tobiasfertig.java.solidityartist.elements.functions.FunctionElement;
import com.tobiasfertig.java.solidityartist.elements.functions.ModifierElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.EnumElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.StructElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.UsingForElement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InterfaceVisitorTests
{
	private Visitor visitor;

	@Before
	public void setUp( )
	{
		this.visitor = new InterfaceVisitor( );
	}

	@Test
	public void testVisitDataTypeElement_WithName_CorrectStringReturned( )
	{
		DataTypeElement dataTypeElement = DataTypeElement.STRING;

		dataTypeElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string", this.visitor.export() );
	}

	@Test
	public void testVisitEnumElement_With4Values_CorrectStringReturned( )
	{
		EnumElement enumElement = EnumElement.builder( "Actions" )
											 .addValue( "GoLeft" )
											 .addValue( "GoRight" )
											 .addValue( "GoStraight" )
											 .addValue( "SitStill" )
											 .build( );

		enumElement.accept( this.visitor );

		String expected = "enum Actions {\n" +
			"    GoLeft,\n" +
			"    GoRight,\n" +
			"    GoStraight,\n" +
			"    SitStill\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitEnumElement_With1Value_CorrectStringReturned( )
	{
		EnumElement enumElement = EnumElement.builder( "Actions" )
											 .addValue( "GoLeft" )
											 .build( );

		enumElement.accept( this.visitor );

		String expected = "enum Actions {\n" +
			"    GoLeft\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitEventElement_With3Parameter_CorrectStringReturned( )
	{
		ParameterElement from = ParameterElement.builder( DataTypeElement.ADDRESS )
												.isIndexedEventParameter( )
												.addName( "from" )
												.build( );

		ParameterElement id = ParameterElement.builder( DataTypeElement.BYTES32 )
											  .isIndexedEventParameter( )
											  .addName( "id" )
											  .build( );

		ParameterElement value = ParameterElement.builder( DataTypeElement.UINT )
												 .addName( "value" )
												 .build( );

		EventElement event = EventElement.builder( "Deposit" )
										 .addParameter( from )
										 .addParameter( id )
										 .addParameter( value )
										 .build( );

		event.accept( this.visitor );

		String expected = "event Deposit(address indexed from, bytes32 indexed id, uint value);";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitEventElement_With1Parameter_CorrectStringReturned( )
	{
		ParameterElement from = ParameterElement.builder( DataTypeElement.ADDRESS )
												.isIndexedEventParameter( )
												.addName( "from" )
												.build( );

		EventElement event = EventElement.builder( "Deposit" )
										 .addParameter( from )
										 .build( );

		event.accept( this.visitor );

		String expected = "event Deposit(address indexed from);";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitEventParameterElement_WithNameBeforeAnonymous_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.isAnonymousEventParameter( )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string anonymous test", this.visitor.export( ) );
	}

	@Test
	public void testVisitEventParameterElement_WithNameAfterAnonymous_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.isAnonymousEventParameter( )
															.addName( "test" )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string anonymous test", this.visitor.export( ) );
	}

	@Test
	public void testVisitEventParameterElement_WithNameBeforeIndexed_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.isIndexedEventParameter( )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string indexed test", this.visitor.export( ) );
	}

	@Test
	public void testVisitEventParameterElement_WithNameAfterIndexed_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.isIndexedEventParameter( )
															.addName( "test" )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string indexed test", this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FallbackFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( ).addStatement( "x = 1" ).build( );
		FunctionElement fallback = FunctionElement.fallbackBuilder( ).addCode( code ).build( );

		fallback.accept( this.visitor );

		String expected = "function() external;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FallbackFunctionPayable_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.fallbackBuilder( )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function() external payable;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_PureFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.externalBuilder( "pureFunction" )
												  .isPure( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function pureFunction() external pure;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_ViewFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.externalBuilder( "viewFunction" )
												  .isView( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function viewFunction() external view;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_PayableFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.externalBuilder( "payableFunction" )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction() external payable;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWith1Parameter_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.inMemory( )
															.build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.externalBuilder( "payableFunction" )
												  .addParameter( parameterElement )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction(string memory test) external payable;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWith2Parameters_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.inMemory( )
															.build( );
		ParameterElement parameterElement2 = ParameterElement.builder( DataTypeElement.ADDRESS )
															 .addName( "test2" )
															 .build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.externalBuilder( "payableFunction" )
												  .addParameter( parameterElement )
												  .addParameter( parameterElement2 )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction(string memory test, address test2) external payable;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWith1ReturnParameter_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.inMemory( )
															.build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.externalBuilder( "payableFunction" )
												  .addReturnParameter( parameterElement )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction() external payable returns(string memory test);";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWith2ReturnParameters_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.inMemory( )
															.build( );
		ParameterElement parameterElement2 = ParameterElement.builder( DataTypeElement.ADDRESS )
															 .addName( "test2" )
															 .build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.externalBuilder( "payableFunction" )
												  .addReturnParameter( parameterElement )
												  .addReturnParameter( parameterElement2 )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction() external payable returns(string memory test, address test2);";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWith1UnnamedReturnParameter_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inMemory( )
															.build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.externalBuilder( "payableFunction" )
												  .addReturnParameter( parameterElement )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction() external payable returns(string memory);";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWith2UnnamedReturnParameters_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inMemory( )
															.build( );
		ParameterElement parameterElement2 = ParameterElement.builder( DataTypeElement.ADDRESS )
															 .build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement fallback = FunctionElement.externalBuilder( "payableFunction" )
												  .addReturnParameter( parameterElement )
												  .addReturnParameter( parameterElement2 )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		fallback.accept( this.visitor );

		String expected = "function payableFunction() external payable returns(string memory, address);";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionTypeElement_WithoutParameters_CorrectStringReturned( )
	{
		FunctionTypeElement element = FunctionTypeElement.externalBuilder( ).build( );

		element.accept( this.visitor );

		String expected = "function() external";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionTypeElement_With2Parameters_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inMemory( )
															.build( );
		ParameterElement parameterElement2 = ParameterElement.builder( DataTypeElement.ADDRESS )
															 .build( );
		FunctionTypeElement element = FunctionTypeElement.externalBuilder( )
														 .addParameter( parameterElement )
														 .addParameter( parameterElement2 )
														 .build( );

		element.accept( this.visitor );

		String expected = "function(string memory, address) external";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionTypeElement_With1Parameter_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inMemory( )
															.build( );
		FunctionTypeElement element = FunctionTypeElement.internalBuilder( )
														 .addParameter( parameterElement )
														 .build( );

		element.accept( this.visitor );

		String expected = "function(string memory) internal";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionTypeElement_Pure_CorrectStringReturned( )
	{
		FunctionTypeElement element = FunctionTypeElement.externalBuilder( ).isPure( ).build( );

		element.accept( this.visitor );

		String expected = "function() external pure";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionTypeElement_View_CorrectStringReturned( )
	{
		FunctionTypeElement element = FunctionTypeElement.externalBuilder( ).isView( ).build( );

		element.accept( this.visitor );

		String expected = "function() external view";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionTypeElement_Payable_CorrectStringReturned( )
	{
		FunctionTypeElement element = FunctionTypeElement.externalBuilder( ).isPayable( ).build( );

		element.accept( this.visitor );

		String expected = "function() external payable";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionTypeElement_WithReturnParameter_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inMemory( )
															.build( );
		FunctionTypeElement element = FunctionTypeElement.externalBuilder( )
														 .addReturnParameter( parameterElement )
														 .build( );

		element.accept( this.visitor );

		String expected = "function() external returns(string memory)";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitInterfaceElement_Empty_CorrectStringReturned( )
	{
		InterfaceElement interfaceElement = InterfaceElement.builder( "IERC20" ).build( );

		interfaceElement.accept( this.visitor );

		String expected = "interface IERC20 {\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitInterfaceElement_WithComment_CorrectStringReturned( )
	{
		NatSpecElement comment = NatSpecElement.builder( )
											   .addTagAtTitle( "An ERC20 Token Contract" )
											   .addTagAtAuthor( "Tobias Fertig" )
											   .isMultiLineComment( )
											   .build( );
		InterfaceElement interfaceElement = InterfaceElement.builder( "IERC20" )
															.addNatSpec( comment )
															.build( );

		interfaceElement.accept( this.visitor );

		String expected = "/**\n" +
			" * @title An ERC20 Token Contract\n" +
			" * @author Tobias Fertig\n" +
			" */\n" +
			"interface IERC20 {\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitInterfaceElement_Complete_CorrectStringReturned( )
	{
		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );

		EnumElement enumElement = EnumElement.builder( "Actions" ).addValue( "X" ).addValue( "Y" ).build( );

		StructElement struct =
			StructElement.builder( "Funder" )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.ADDRESS ).addName( "funder" ).build( ) )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.UINT ).addName( "amount" ).build( ) ).build( );
		EventElement event =
			EventElement.builder( "Transfer" )
						.addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
													   .isIndexedEventParameter( )
													   .addName( "sender" )
													   .build( ) )
						.addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
													   .isIndexedEventParameter( )
													   .addName( "recipient" )
													   .build( )
						)
						.addParameter(
							ParameterElement.builder( DataTypeElement.UINT256 ).addName( "amount" ).build( ) )
						.build( );

		ModifierElement modifier =
			ModifierElement.builder( "onlyOwner" )
						   .addCodeWithoutUnderscoreStatement(
							   CodeElement.builder( ).addStatement( "require(owner == msg.sender)" ).build( ) )
						   .build( );

		FunctionElement fallbackFunction =
			FunctionElement.fallbackBuilder( )
						   .addCode( CodeElement.builder( ).addStatement( "owner = msg.sender" ).build( ) )
						   .build( );

		FunctionElement total = FunctionElement.externalBuilder( "total" )
											   .isView( )
											   .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																					.build( )
											   )
											   .build( );

		InterfaceElement interfaceElement = InterfaceElement.builder( "Test" )
															.addUsingForDeclaration( usingFor )
															.addEnumDeclaration( enumElement )
															.addStructDeclaration( struct )
															.addEventDeclaration( event )
															.addModifierDeclaration( modifier )
															.addFallbackFunction( fallbackFunction )
															.addExternalFunction( total )
															.build( );

		interfaceElement.accept( this.visitor );
		String expected = "interface Test {\n" +
			"    using SafeMath for uint256;\n" +
			"\n" +
			"    enum Actions {\n" +
			"        X,\n" +
			"        Y\n" +
			"    }\n" +
			"\n" +
			"    struct Funder {\n" +
			"        address funder;\n" +
			"        uint amount;\n" +
			"    }\n" +
			"\n" +
			"    event Transfer(address indexed sender, address indexed recipient, uint256 amount);\n" +
			"\n" +
			"    modifier onlyOwner() {\n" +
			"        require(owner == msg.sender);\n" +
			"        _;\n" +
			"    }\n" +
			"\n" +
			"    function() external;\n" +
			"\n" +
			"    function total() external view returns(uint256);\n" +
			"\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitMappingElement_WithKeyAndValue_CorrectStringReturned( )
	{
		MappingElement mapping = new MappingElement( DataTypeElement.ADDRESS, DataTypeElement.UINT256 );

		mapping.accept( this.visitor );

		String expected = "mapping(address => uint256)";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitMappingElement_WithMappingAsValue_CorrectStringReturned( )
	{
		MappingElement mapping = new MappingElement( DataTypeElement.ADDRESS,
			new MappingElement( DataTypeElement.ADDRESS, DataTypeElement.UINT256 ) );

		mapping.accept( this.visitor );

		String expected = "mapping(address => mapping(address => uint256))";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitMemoryParameterElement_WithNameBeforeMemory_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.inMemory( )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string memory test", this.visitor.export( ) );
	}

	@Test
	public void testVisitMemoryParameterElement_WithNameAfterMemory_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inMemory( )
															.addName( "test" )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string memory test", this.visitor.export( ) );
	}

	@Test
	public void testVisitMemoryParameterElement_WithoutName_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inMemory( )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string memory", this.visitor.export( ) );
	}

	@Test
	public void testVisitModifierElement_WithoutCodeAndParameters_CorrectStringReturned( )
	{
		ModifierElement modifier = ModifierElement.builder( "onlyOwner" )
												  .build( );

		modifier.accept( this.visitor );

		String expected = "modifier onlyOwner() {\n" +
			"    _;\n" +
			"}";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitModifierElement_WithCodeAndWithoutParametersAndUnderscore_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "require(owner = msg.sender)" )
									  .build( );
		ModifierElement modifier = ModifierElement.builder( "onlyOwner" )
												  .addCodeWithoutUnderscoreStatement( code )
												  .build( );

		modifier.accept( this.visitor );

		String expected = "modifier onlyOwner() {\n" +
			"    require(owner = msg.sender);\n" +
			"    _;\n" +
			"}";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitModifierElement_WithComment_CorrectStringReturned( )
	{
		NatSpecElement comment = NatSpecElement.builder( )
											   .addTagAtNotice(
												   "This modifier checks if the sender of a transaction is the " +
													   "contract owner." )
											   .isMultiLineComment( )
											   .build( );

		CodeElement code = CodeElement.builder( )
									  .addStatement( "require(owner = msg.sender)" )
									  .build( );
		ModifierElement modifier = ModifierElement.builder( "onlyOwner" )
												  .addNatSpec( comment )
												  .addCodeWithoutUnderscoreStatement( code )
												  .build( );

		modifier.accept( this.visitor );

		String expected = "/**\n" +
			" * @notice This modifier checks if the sender of a transaction is the contract owner.\n" +
			" */\n" +
			"modifier onlyOwner() {\n" +
			"    require(owner = msg.sender);\n" +
			"    _;\n" +
			"}";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitModifierElement_WithCodeAndWithoutParameters_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "require(owner = msg.sender)" )
									  .addStatement( "_" )
									  .build( );
		ModifierElement modifier = ModifierElement.builder( "onlyOwner" )
												  .addCode( code )
												  .build( );

		modifier.accept( this.visitor );

		String expected = "modifier onlyOwner() {\n" +
			"    require(owner = msg.sender);\n" +
			"    _;\n" +
			"}";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitModifierElement_WithCodeAnd1Parameter_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.ADDRESS )
															.addName( "test" )
															.build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "require(test = msg.sender)" )
									  .build( );
		ModifierElement modifier = ModifierElement.builder( "onlyOwner" )
												  .addParameter( parameterElement )
												  .addCodeWithoutUnderscoreStatement( code )
												  .build( );

		modifier.accept( this.visitor );

		String expected = "modifier onlyOwner(address test) {\n" +
			"    require(test = msg.sender);\n" +
			"    _;\n" +
			"}";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitModifierElement_WithCodeAnd2Parameters_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.ADDRESS )
															.addName( "test" )
															.build( );
		ParameterElement parameterElement2 = ParameterElement.builder( DataTypeElement.ADDRESS )
															 .addName( "test2" )
															 .build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "require(test = msg.sender)" )
									  .build( );
		ModifierElement modifier = ModifierElement.builder( "onlyOwner" )
												  .addParameter( parameterElement )
												  .addParameter( parameterElement2 )
												  .addCodeWithoutUnderscoreStatement( code )
												  .build( );

		modifier.accept( this.visitor );

		String expected = "modifier onlyOwner(address test, address test2) {\n" +
			"    require(test = msg.sender);\n" +
			"    _;\n" +
			"}";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitNatSpecElement_SingleLine_CorrectStringReturned( )
	{
		NatSpecElement comment = NatSpecElement.builder( )
											   .addTagAtTitle( "NatSpec Example" )
											   .addLine( "" )
											   .addTagAtAuthor( "Tobias Fertig" )
											   .build( );

		comment.accept( this.visitor );
		String expected = "/// @title NatSpec Example\n" +
			"/// \n" +
			"/// @author Tobias Fertig";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitNatSpecElement_MultiLine_CorrectStringReturned( )
	{
		NatSpecElement comment = NatSpecElement.builder( )
											   .addTagAtTitle( "NatSpec Example" )
											   .addLine( "" )
											   .addTagAtAuthor( "Tobias Fertig" )
											   .isMultiLineComment( )
											   .build( );

		comment.accept( this.visitor );
		String expected = "/**\n" +
			" * @title NatSpec Example\n" +
			" * \n" +
			" * @author Tobias Fertig\n" +
			" */";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitParameterElement_WithName_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.ADDRESS )
															.addName( "test" )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "address test", this.visitor.export( ) );
	}

	@Test
	public void testVisitParameterElement_WithoutName_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.ADDRESS )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "address", this.visitor.export( ) );
	}

	@Test
	public void testVisitStorageParameterElement_WithNameBeforeStorage_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "test" )
															.inStorage( )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string storage test", this.visitor.export( ) );
	}

	@Test
	public void testVisitStorageParameterElement_WithNameAfterStorage_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inStorage( )
															.addName( "test" )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string storage test", this.visitor.export( ) );
	}

	@Test
	public void testVisitStorageParameterElement_WithoutName_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inStorage( )
															.build( );

		parameterElement.accept( this.visitor );
		assertEquals( "Should be the same text", "string storage", this.visitor.export( ) );
	}

	@Test
	public void testVisitStructElement_With1Member_CorrectStringReturned( )
	{
		ParameterElement addr = ParameterElement.builder( DataTypeElement.ADDRESS ).addName( "addr" ).build( );
		ParameterElement amount = ParameterElement.builder( DataTypeElement.UINT ).addName( "amount" ).build( );
		StructElement structElement = StructElement.builder( "Finder" )
												   .addStructMember( addr )
												   .addStructMember( amount )
												   .build( );

		structElement.accept( this.visitor );

		String expected = "struct Finder {\n" +
			"    address addr;\n" +
			"    uint amount;\n" +
			"}";
		assertEquals( "Should be the same object", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitUsingForElement_WithCustomExtensionAndSource_CorrectStringReturned( )
	{
		UsingForElement usingForElement = UsingForElement.builder( "X", "Y" ).build( );

		usingForElement.accept( this.visitor );
		assertEquals( "Should be the same text", "using X for Y;", this.visitor.export( ) );
	}
}
