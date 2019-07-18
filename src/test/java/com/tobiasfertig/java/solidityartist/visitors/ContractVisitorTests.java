package com.tobiasfertig.java.solidityartist.visitors;

import com.tobiasfertig.java.solidityartist.elements.comments.NatSpecElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.FunctionTypeElement;
import com.tobiasfertig.java.solidityartist.elements.datatypes.MappingElement;
import com.tobiasfertig.java.solidityartist.elements.events.EventElement;
import com.tobiasfertig.java.solidityartist.elements.files.ContractElement;
import com.tobiasfertig.java.solidityartist.elements.functions.CodeElement;
import com.tobiasfertig.java.solidityartist.elements.functions.ConstructorElement;
import com.tobiasfertig.java.solidityartist.elements.functions.FunctionElement;
import com.tobiasfertig.java.solidityartist.elements.functions.ModifierElement;
import com.tobiasfertig.java.solidityartist.elements.parameters.ParameterElement;
import com.tobiasfertig.java.solidityartist.elements.statevariables.StateVariableElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.EnumElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.StructElement;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.UsingForElement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContractVisitorTests
{
	private Visitor visitor;

	@Before
	public void setUp( )
	{
		this.visitor = new ContractVisitor( 99 );
	}

	@Test
	public void testVisitCodeElement_With4Line_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addCode( "if (!self.flags[value]) {" )
									  .addStatement( "    return false" )
									  .addStatement( "    self.flags[value] = false" )
									  .addStatement( "    return true" )
									  .addCode( "}" )
									  .build( );

		code.accept( this.visitor );

		String expected = "if (!self.flags[value]) {\n" +
			"    return false;\n" +
			"    self.flags[value] = false;\n" +
			"    return true;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitCodeElement_With1Line_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "self.flags[value] = false" )
									  .build( );

		code.accept( this.visitor );

		String expected = "self.flags[value] = false;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitCodeElement_WithoutLines_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( ).build( );
		code.accept( this.visitor );
		assertEquals( "Should be the same text", "", this.visitor.export( ) );
	}

	@Test
	public void testVisitConstructorElement_Default_CorrectStringReturned( )
	{
		ConstructorElement constructor = ConstructorElement.publicBuilder( ).build( );

		constructor.accept( this.visitor );

		String expected = "constructor() public {\n" +
			"\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitConstructorElement_WithCode_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( ).addStatement( "a = _a" ).build( );
		ConstructorElement constructor = ConstructorElement.publicBuilder( ).addCode( code ).build( );

		constructor.accept( this.visitor );

		String expected = "constructor() public {\n" +
			"    a = _a;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitConstructorElement_WithComment_CorrectStringReturned( )
	{
		NatSpecElement comment = NatSpecElement.builder( )
											   .addTagAtNotice( "This constructor is only called at creation." )
											   .build( );
		CodeElement code = CodeElement.builder( ).addStatement( "a = _a" ).build( );
		ConstructorElement constructor = ConstructorElement.publicBuilder( )
														   .addNatSpec( comment )
														   .addCode( code )
														   .build( );

		constructor.accept( this.visitor );

		String expected = "/// @notice This constructor is only called at creation.\n" +
			"constructor() public {\n" +
			"    a = _a;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitConstructorElement_With1Parameter_CorrectStringReturned( )
	{
		ParameterElement parameter = ParameterElement.builder( DataTypeElement.UINT ).addName( "_y" ).build( );
		ConstructorElement constructor = ConstructorElement.publicBuilder( ).addParameter( parameter ).build( );

		constructor.accept( this.visitor );

		String expected = "constructor(uint _y) public {\n" +
			"\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitConstructorElement_With2Parameters_CorrectStringReturned( )
	{
		ParameterElement parameter = ParameterElement.builder( DataTypeElement.UINT ).addName( "_y" ).build( );
		ParameterElement parameter2 = ParameterElement.builder( DataTypeElement.UINT ).addName( "_x" ).build( );
		ConstructorElement constructor = ConstructorElement.publicBuilder( )
														   .addParameter( parameter )
														   .addParameter( parameter2 )
														   .build( );

		constructor.accept( this.visitor );

		String expected = "constructor(uint _y, uint _x) public {\n" +
			"\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitConstructorElement_Payable_CorrectStringReturned( )
	{
		ConstructorElement constructor = ConstructorElement.publicBuilder( ).isPayable( ).build( );

		constructor.accept( this.visitor );

		String expected = "constructor() public payable {\n" +
			"\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitConstructorElement_Abstract_CorrectStringReturned( )
	{
		ConstructorElement constructor = ConstructorElement.internalBuilder( ).build( );

		constructor.accept( this.visitor );

		String expected = "constructor() internal {\n" +
			"\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitConstructorElement_WithInheritanceModifiers_CorrectStringReturned( )
	{
		ParameterElement parameter = ParameterElement.builder( DataTypeElement.UINT ).addName( "_y" ).build( );
		ConstructorElement constructor = ConstructorElement.publicBuilder( )
														   .addInheritanceModifier( "Base(_y * _y)" )
														   .addParameter( parameter )
														   .build( );

		constructor.accept( this.visitor );

		String expected = "constructor(uint _y) Base(_y * _y) public {\n" +
			"\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitContractElement_EmptyContract_CorrectStringReturned( )
	{
		ContractElement contract = ContractElement.builder( "ERC20" ).build( );

		contract.accept( this.visitor );
		String expected = "contract ERC20 {\n" +
			"\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitContractElement_WithDefaultConstructor_CorrectStringReturned( )
	{
		ConstructorElement constructor = ConstructorElement.publicBuilder( ).build( );
		ContractElement contract = ContractElement.builder( "ERC20" )
												  .addConstructor( constructor )
												  .build( );

		contract.accept( this.visitor );
		String expected = "contract ERC20 {\n" +
			"    constructor() public {\n" +
			"\n" +
			"    }\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitContractElement_WithUsingFor_CorrectStringReturned( )
	{
		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );
		ConstructorElement constructor = ConstructorElement.publicBuilder( ).build( );
		ContractElement contract = ContractElement.builder( "ERC20" )
												  .addUsingForDeclaration( usingFor )
												  .addConstructor( constructor )
												  .build( );

		contract.accept( this.visitor );
		String expected = "contract ERC20 {\n" +
			"    using SafeMath for uint256;\n" +
			"\n" +
			"    constructor() public {\n" +
			"\n" +
			"    }\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitContractElement_WithComment_CorrectStringReturned( )
	{
		NatSpecElement comment = NatSpecElement.builder( )
											   .addTagAtTitle( "An ERC20 Token Contract" )
											   .addTagAtAuthor( "Tobias Fertig" )
											   .isMultiLineComment( )
											   .build( );
		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );
		ConstructorElement constructor = ConstructorElement.publicBuilder( ).build( );
		ContractElement contract = ContractElement.builder( "ERC20" )
												  .addNatSpec( comment )
												  .addUsingForDeclaration( usingFor )
												  .addConstructor( constructor )
												  .build( );

		contract.accept( this.visitor );
		String expected = "/**\n" +
			" * @title An ERC20 Token Contract\n" +
			" * @author Tobias Fertig\n" +
			" */\n" +
			"contract ERC20 {\n" +
			"    using SafeMath for uint256;\n" +
			"\n" +
			"    constructor() public {\n" +
			"\n" +
			"    }\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitContractElement_WithStateVariables_CorrectStringReturned( )
	{
		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );
		StateVariableElement totalSupply = StateVariableElement.builder( DataTypeElement.UINT256, "_totalSupply" )
															   .isPrivate( )
															   .build( );
		ConstructorElement constructor = ConstructorElement.publicBuilder( ).build( );
		ContractElement contract = ContractElement.builder( "ERC20" )
												  .addUsingForDeclaration( usingFor )
												  .addStateVariable( totalSupply )
												  .addConstructor( constructor )
												  .build( );

		contract.accept( this.visitor );
		String expected = "contract ERC20 {\n" +
			"    using SafeMath for uint256;\n" +
			"\n" +
			"    uint256 private _totalSupply;\n" +
			"\n" +
			"    constructor() public {\n" +
			"\n" +
			"    }\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitContractElement_WithInheritedContracts_CorrectStringReturned( )
	{
		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );
		StateVariableElement totalSupply = StateVariableElement.builder( DataTypeElement.UINT256, "_totalSupply" )
															   .isPrivate( )
															   .build( );
		ConstructorElement constructor = ConstructorElement.publicBuilder( ).build( );
		ContractElement contract = ContractElement.builder( "ERC20" )
												  .addInheritedContract( "IERC20" )
												  .addUsingForDeclaration( usingFor )
												  .addStateVariable( totalSupply )
												  .addConstructor( constructor )
												  .build( );

		contract.accept( this.visitor );
		String expected = "contract ERC20 is IERC20 {\n" +
			"    using SafeMath for uint256;\n" +
			"\n" +
			"    uint256 private _totalSupply;\n" +
			"\n" +
			"    constructor() public {\n" +
			"\n" +
			"    }\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitContractElement_WithFunction_CorrectStringReturned( )
	{
		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );
		StateVariableElement _totalSupply = StateVariableElement.builder( DataTypeElement.UINT256, "_totalSupply" )
															   .isPrivate( )
															   .build( );
		ConstructorElement constructor = ConstructorElement.publicBuilder( ).build( );
		FunctionElement totalSupply = FunctionElement.publicBuilder( "totalSupply" )
													 .isView( )
													 .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																						  .build( )
													 )
													 .addCode( CodeElement.builder( )
																		  .addStatement( "return _totalSupply" )
																		  .build( )
													 )
													 .build( );
		ContractElement contract = ContractElement.builder( "ERC20" )
												  .addInheritedContract( "IERC20" )
												  .addUsingForDeclaration( usingFor )
												  .addStateVariable( _totalSupply )
												  .addConstructor( constructor )
												  .addPublicFunction( totalSupply )
												  .build( );

		contract.accept( this.visitor );
		String expected = "contract ERC20 is IERC20 {\n" +
			"    using SafeMath for uint256;\n" +
			"\n" +
			"    uint256 private _totalSupply;\n" +
			"\n" +
			"    constructor() public {\n" +
			"\n" +
			"    }\n" +
			"\n" +
			"    function totalSupply() public view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitContractElement_WithInternalFunction_CorrectStringReturned( )
	{
		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );
		StateVariableElement _totalSupply = StateVariableElement.builder( DataTypeElement.UINT256, "_totalSupply" )
																.isPrivate( )
																.build( );
		ConstructorElement constructor = ConstructorElement.publicBuilder( ).build( );
		FunctionElement totalSupply = FunctionElement.publicBuilder( "totalSupply" )
													 .isView( )
													 .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																						  .build( )
													 )
													 .addCode( CodeElement.builder( )
																		  .addStatement( "return _totalSupply" )
																		  .build( )
													 )
													 .build( );
		FunctionElement _transfer = FunctionElement.internalBuilder( "_transfer" )
												   .addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
																				  .addName( "sender" )
																				  .build( ) )
												   .addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
																				  .addName( "recipient" )
																				  .build( )
												   )
												   .addParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																				  .addName( "amount" )
																				  .build( )
												   )
												   .addCode( CodeElement.builder( )
																		.addStatement(
																			"require(sender != address(0), \"ERC20: transfer from the zero address\")" )
																		.addStatement(
																			"require(recipient != address(0), \"ERC20: transfer to the zero address\")" )
																		.addStatement( "_balances[sender] = " +
																			"_balances[sender].sub(amount)" )
																		.addStatement(
																			"_balances[recipient] = _balances[recipient].add(amount)" )
																		.addStatement(
																			"emit Transfer(sender, recipient, amount)" )
																		.build( )
												   )
												   .build( );
		ContractElement contract = ContractElement.builder( "ERC20" )
												  .addInheritedContract( "IERC20" )
												  .addUsingForDeclaration( usingFor )
												  .addStateVariable( _totalSupply )
												  .addConstructor( constructor )
												  .addPublicFunction( totalSupply )
												  .addInternalFunction( _transfer )
												  .build( );

		contract.accept( this.visitor );
		String expected = "contract ERC20 is IERC20 {\n" +
			"    using SafeMath for uint256;\n" +
			"\n" +
			"    uint256 private _totalSupply;\n" +
			"\n" +
			"    constructor() public {\n" +
			"\n" +
			"    }\n" +
			"\n" +
			"    function totalSupply() public view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"\n" +
			"    function _transfer(address sender, address recipient, uint256 amount) internal {\n" +
			"        require(sender != address(0), \"ERC20: transfer from the zero address\");\n" +
			"        require(recipient != address(0), \"ERC20: transfer to the zero address\");\n" +
			"        _balances[sender] = _balances[sender].sub(amount);\n" +
			"        _balances[recipient] = _balances[recipient].add(amount);\n" +
			"        emit Transfer(sender, recipient, amount);\n" +
			"    }\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitContractElement_Complete_CorrectStringReturned( )
	{
		UsingForElement usingFor = UsingForElement.builder( "SafeMath", DataTypeElement.UINT256 ).build( );

		EnumElement enumElement = EnumElement.builder( "Actions" ).addValue( "X" ).addValue( "Y" ).build( );

		StructElement struct =
			StructElement.builder( "Funder" )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.ADDRESS ).addName( "funder" ).build( ) )
						 .addStructMember(
							 ParameterElement.builder( DataTypeElement.UINT ).addName( "amount" ).build( ) ).build( );

		StateVariableElement _totalSupply = StateVariableElement.builder( DataTypeElement.UINT256, "_totalSupply" )
																.isPrivate( )
																.build( );

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

		NatSpecElement modifierComment = NatSpecElement.builder( )
											   .addTagAtNotice(
												   "This modifier checks if the sender of a transaction is the " +
													   "contract owner." )
											   .isMultiLineComment( )
											   .build( );

		ModifierElement modifier =
			ModifierElement.builder( "onlyOwner" )
						   .addNatSpec( modifierComment )
						   .addCodeWithoutUnderscoreStatement(
							   CodeElement.builder( ).addStatement( "require(owner == msg.sender)" ).build( ) )
						   .build( );

		NatSpecElement constructorComment = NatSpecElement.builder( )
														  .addTagAtNotice(
															  "This constructor is only called at creation." )
														  .addLine( "" )
														  .addLine( "This constructor is empty." )
														  .build( );

		ConstructorElement constructor = ConstructorElement.publicBuilder( )
														   .addNatSpec( constructorComment )
														   .build( );

		FunctionElement fallbackFunction =
			FunctionElement.fallbackBuilder( )
						   .addCode( CodeElement.builder( ).addStatement( "owner = msg.sender" ).build( ) )
						   .build( );

		NatSpecElement totalComment = NatSpecElement.builder( )
											   .addTagAtNotice( "This function is a view and returns the total " +
												   "supply of token.")
											   .addTagAtReturn( "uint256 is the total supply of token." )
											   .isMultiLineComment( )
											   .build( );

		FunctionElement total = FunctionElement.externalBuilder( "total" )
											   .addNatSpec( totalComment )
											   .isView( )
											   .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																					.build( )
											   )
											   .addCode( CodeElement.builder( )
																	.addStatement( "return _totalSupply" )
																	.build( )
											   )
											   .build( );

		FunctionElement totalSupply = FunctionElement.publicBuilder( "totalSupply" )
													 .isView( )
													 .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																						  .build( )
													 )
													 .addCode( CodeElement.builder( )
																		  .addStatement( "return _totalSupply" )
																		  .build( )
													 )
													 .build( );

		FunctionElement _transfer = FunctionElement.internalBuilder( "_transfer" )
												   .addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
																				  .addName(
																					  "sender" )
																				  .build( ) )
												   .addParameter( ParameterElement.builder( DataTypeElement.ADDRESS )
																				  .addName(
																					  "recipient" )
																				  .build( )
												   ).isPayable( )
												   .addReturnParameter(
													   ParameterElement.builder( DataTypeElement.UINT256 )
																	   .addName( "amount" )
																	   .build( )
												   )
												   .addCode( CodeElement.builder( )
																		.addStatement(
																			"require(sender != address(0), \"ERC20: transfer from the zero address\")" )
																		.addStatement(
																			"require(recipient != address(0), \"ERC20: transfer to the zero address\")" )
																		.addStatement( "_balances[sender] = " +
																			"_balances[sender].sub(amount)" )
																		.addStatement(
																			"_balances[recipient] = _balances[recipient].add(amount)" )
																		.addStatement(
																			"emit Transfer(sender, recipient, amount)" )
																		.build( )
												   )
												   .build( );

		FunctionElement totally = FunctionElement.privateBuilder( "totally" )
													 .isView( )
													 .addReturnParameter( ParameterElement.builder( DataTypeElement.UINT256 )
																						  .build( )
													 )
													 .addCode( CodeElement.builder( )
																		  .addStatement( "return _totalSupply" )
																		  .build( )
													 )
													 .build( );

		ContractElement contract = ContractElement.builder( "ERC20" )
												  .addInheritedContract( "IERC20" )
												  .addUsingForDeclaration( usingFor )
												  .addEnumDeclaration( enumElement )
												  .addStructDeclaration( struct )
												  .addStateVariable( _totalSupply )
												  .addEventDeclaration( event )
												  .addModifierDeclaration( modifier )
												  .addConstructor( constructor )
												  .addFallbackFunction( fallbackFunction )
												  .addExternalFunction( total )
												  .addPublicFunction( totalSupply )
												  .addInternalFunction( _transfer )
												  .addPrivateFunction( totally )
												  .build( );

		contract.accept( this.visitor );
		String expected = "contract ERC20 is IERC20 {\n" +
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
			"    uint256 private _totalSupply;\n" +
			"\n" +
			"    event Transfer(address indexed sender, address indexed recipient, uint256 amount);\n" +
			"\n" +
			"    /**\n" +
			"     * @notice This modifier checks if the sender of a transaction is the contract owner.\n" +
			"     */\n" +
			"    modifier onlyOwner() {\n" +
			"        require(owner == msg.sender);\n" +
			"        _;\n" +
			"    }\n" +
			"\n" +
			"    /// @notice This constructor is only called at creation.\n" +
			"    /// \n" +
			"    /// This constructor is empty.\n" +
			"    constructor() public {\n" +
			"\n" +
			"    }\n" +
			"\n" +
			"    function() external {\n" +
			"        owner = msg.sender;\n" +
			"    }\n" +
			"\n" +
			"    /**\n" +
			"     * @notice This function is a view and returns the total supply of token.\n" +
			"     * @return uint256 is the total supply of token.\n" +
			"     */\n" +
			"    function total() external view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"\n" +
			"    function totalSupply() public view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"\n" +
			"    function _transfer(address sender, address recipient)\n" +
			"        internal\n" +
			"        payable\n" +
			"        returns(\n" +
			"            uint256 amount\n" +
			"        )\n" +
			"    {\n" +
			"        require(sender != address(0), \"ERC20: transfer from the zero address\");\n" +
			"        require(recipient != address(0), \"ERC20: transfer to the zero address\");\n" +
			"        _balances[sender] = _balances[sender].sub(amount);\n" +
			"        _balances[recipient] = _balances[recipient].add(amount);\n" +
			"        emit Transfer(sender, recipient, amount);\n" +
			"    }\n" +
			"\n" +
			"    function totally() private view returns(uint256) {\n" +
			"        return _totalSupply;\n" +
			"    }\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
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
	public void testVisitFunctionElement_UntilParametersInline_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "testSuperLongLong" )
															.inMemory( )
															.build( );
		ParameterElement parameterElement2 = ParameterElement.builder( DataTypeElement.ADDRESS )
															 .addName( "test2SuperLongLong" )
															 .build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement function = FunctionElement.publicBuilder( "payableFunction" )
												  .addReturnParameter( parameterElement )
												  .addReturnParameter( parameterElement2 )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		function.accept( this.visitor );

		String expected = "function payableFunction()\n" +
			"    public\n" +
			"    payable\n" +
			"    returns(\n" +
			"        string memory testSuperLongLong,\n" +
			"        address test2SuperLongLong\n" +
			"    )\n" +
			"{\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_LineWrapped_CorrectStringReturned( )
	{
		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.addName( "testSuperLongLongLongLongLong" )
															.inMemory( )
															.build( );
		ParameterElement parameterElement2 = ParameterElement.builder( DataTypeElement.ADDRESS )
															 .addName( "test2SuperLongLongLongLongLong" )
															 .build( );
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement function = FunctionElement.publicBuilder( "payableFunction" )
												  .addParameter( parameterElement )
												  .addParameter( parameterElement2 )
												  .addReturnParameter( parameterElement )
												  .addReturnParameter( parameterElement2 )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		function.accept( this.visitor );

		String expected = "function payableFunction(\n" +
			"    string memory testSuperLongLongLongLongLong,\n" +
			"    address test2SuperLongLongLongLongLong\n" +
			")\n" +
			"    public\n" +
			"    payable\n" +
			"    returns(\n" +
			"        string memory testSuperLongLongLongLongLong,\n" +
			"        address test2SuperLongLongLongLongLong\n" +
			"    )\n" +
			"{\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FallbackFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( ).addStatement( "x = 1" ).build( );
		FunctionElement fallback = FunctionElement.fallbackBuilder( ).addCode( code ).build( );

		fallback.accept( this.visitor );

		String expected = "function() external {\n" +
			"    x = 1;\n" +
			"}";
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

		String expected = "function() external payable {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_PureFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement function = FunctionElement.publicBuilder( "pureFunction" )
												  .isPure( )
												  .addCode( code )
												  .build( );

		function.accept( this.visitor );

		String expected = "function pureFunction() public pure {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_ViewFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement function = FunctionElement.publicBuilder( "viewFunction" )
												  .isView( )
												  .addCode( code )
												  .build( );

		function.accept( this.visitor );

		String expected = "function viewFunction() public view {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_AbstractFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement function = FunctionElement.publicBuilder( "abstractFunction" )
												  .isAbstract( )
												  .addCode( code )
												  .build( );

		function.accept( this.visitor );

		String expected = "function abstractFunction() public;";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_PayableFunction_CorrectStringReturned( )
	{
		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );
		FunctionElement function = FunctionElement.publicBuilder( "payableFunction" )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		function.accept( this.visitor );

		String expected = "function payableFunction() public payable {\n" +
			"    x = 1;\n" +
			"}";
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
		FunctionElement function = FunctionElement.publicBuilder( "payableFunction" )
												  .addParameter( parameterElement )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		function.accept( this.visitor );

		String expected = "function payableFunction(string memory test) public payable {\n" +
			"    x = 1;\n" +
			"}";
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
		FunctionElement function = FunctionElement.publicBuilder( "payableFunction" )
												  .addParameter( parameterElement )
												  .addParameter( parameterElement2 )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		function.accept( this.visitor );

		String expected = "function payableFunction(string memory test, address test2) public payable {\n" +
			"    x = 1;\n" +
			"}";
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
		FunctionElement function = FunctionElement.publicBuilder( "payableFunction" )
												  .addReturnParameter( parameterElement )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		function.accept( this.visitor );

		String expected = "function payableFunction() public payable returns(string memory test) {\n" +
			"    x = 1;\n" +
			"}";
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
		FunctionElement function = FunctionElement.publicBuilder( "payableFunction" )
												  .addReturnParameter( parameterElement )
												  .addReturnParameter( parameterElement2 )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		function.accept( this.visitor );

		String expected = "function payableFunction() public payable returns(string memory test, address test2) {\n" +
			"    x = 1;\n" +
			"}";
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
		FunctionElement function = FunctionElement.publicBuilder( "payableFunction" )
												  .addReturnParameter( parameterElement )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		function.accept( this.visitor );

		String expected = "function payableFunction() public payable returns(string memory) {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWithComment_CorrectStringReturned( )
	{
		NatSpecElement comment = NatSpecElement.builder( )
											   .addTagAtNotice( "This function is payable, but the ether is lost if " +
												   "you send it." )
											   .addTagAtReturn( "string is a nice string." )
											   .isMultiLineComment( )
											   .build( );

		ParameterElement parameterElement = ParameterElement.builder( DataTypeElement.STRING )
															.inMemory( )
															.build( );

		CodeElement code = CodeElement.builder( )
									  .addStatement( "x = 1" )
									  .build( );

		FunctionElement function = FunctionElement.publicBuilder( "payableFunction" )
												  .addNatSpec( comment )
												  .addReturnParameter( parameterElement )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		function.accept( this.visitor );

		String expected = "/**\n" +
			" * @notice This function is payable, but the ether is lost if you send it.\n" +
			" * @return string is a nice string.\n" +
			" */\n" +
			"function payableFunction() public payable returns(string memory) {\n" +
			"    x = 1;\n" +
			"}";
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
		FunctionElement function = FunctionElement.publicBuilder( "payableFunction" )
												  .addReturnParameter( parameterElement )
												  .addReturnParameter( parameterElement2 )
												  .isPayable( )
												  .addCode( code )
												  .build( );

		function.accept( this.visitor );

		String expected = "function payableFunction() public payable returns(string memory, address) {\n" +
			"    x = 1;\n" +
			"}";
		assertEquals( "Should be the same text", expected, this.visitor.export( ) );
	}

	@Test
	public void testVisitFunctionElement_FunctionWithInternalIndentation_CorrectStringReturned( )
	{
		NatSpecElement comment = NatSpecElement.builder( )
											   .isMultiLineComment( )
											   .addTagAtDev(
												   "Internal function to invoke `onERC721Received` on a target address." )
											   .addLine(
												   "The call is not executed if the target address is not a contract." )
											   .addLine( "" )
											   .addLine( "This function is deprecated." )
											   .addTagAtParam( "from address representing the previous owner of the " +
												   "given token ID" )
											   .addTagAtParam( "to target address that will receive the tokens" )
											   .addTagAtParam( "tokenId uint256 ID of the token to be transferred" )
											   .addTagAtParam( "_data bytes optional data to send along with the call" )
											   .addTagAtReturn(
												   "bool whether the call correctly returned the expected magic value" )
											   .build( );

		ParameterElement from =
			ParameterElement.builder( DataTypeElement.ADDRESS ).addName( "from" ).build( );

		ParameterElement to = ParameterElement.builder( DataTypeElement.ADDRESS ).addName( "to" ).build( );

		ParameterElement tokenId = ParameterElement.builder( DataTypeElement.UINT256 ).addName( "tokenId" ).build( );

		ParameterElement _data = ParameterElement.builder( DataTypeElement.BYTES )
												 .addName( "_data" )
												 .inMemory( )
												 .build( );

		ParameterElement returnBool = ParameterElement.builder( DataTypeElement.BOOL ).build( );

		CodeElement code = CodeElement.builder( )
									  .addCode( "if(!to.isContract()) {" )
									  .addCode( "$>return true;" )
									  .addCode( "}" )
									  .addCode( "" )
									  .addCode( "bytes4 retval = IERC721Receiver(to).onERC721Received(msg.sender, " +
										  "from, tokenId, _data);" )
									  .addCode( "return (retval == _ERC721_RECEIVED);" )
									  .build( );

		FunctionElement functionElement = FunctionElement.internalBuilder( "_checkOnERC721Received" )
														 .addNatSpec( comment )
														 .addParameter( from )
														 .addParameter( to )
														 .addParameter( tokenId )
														 .addParameter( _data )
														 .addReturnParameter( returnBool )
														 .addCode( code )
														 .build( );

		functionElement.accept( this.visitor );

		String expected = "/**\n" +
			" * @dev Internal function to invoke `onERC721Received` on a target address.\n" +
			" * The call is not executed if the target address is not a contract.\n" +
			" * \n" +
			" * This function is deprecated.\n" +
			" * @param from address representing the previous owner of the given token ID\n" +
			" * @param to target address that will receive the tokens\n" +
			" * @param tokenId uint256 ID of the token to be transferred\n" +
			" * @param _data bytes optional data to send along with the call\n" +
			" * @return bool whether the call correctly returned the expected magic value\n" +
			" */\n" +
			"function _checkOnERC721Received(address from, address to, uint256 tokenId, bytes memory _data)\n" +
			"    internal\n" +
			"    returns(\n" +
			"        bool\n" +
			"    )\n" +
			"{\n" +
			"    if(!to.isContract()) {\n" +
			"        return true;\n" +
			"    }\n" +
			"    \n" +
			"    bytes4 retval = IERC721Receiver(to).onERC721Received(msg.sender, from, tokenId, _data);\n" +
			"    return (retval == _ERC721_RECEIVED);\n" +
			"}";
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
	public void testVisitModifierElement_WithCodeAndComment_CorrectStringReturned( )
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
	public void testVisitStateVariableElement_WithNamePublic_CorrectStringReturned( )
	{
		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.ADDRESS, "owner" )
																 .isPublic( )
																 .build( );

		stateVariable.accept( this.visitor );
		assertEquals( "Should be the same text", "address public owner;", this.visitor.export( ) );
	}

	@Test
	public void testVisitStateVariableElement_WithNamePrivate_CorrectStringReturned( )
	{
		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.ADDRESS, "owner" )
																 .isPrivate( )
																 .build( );

		stateVariable.accept( this.visitor );
		assertEquals( "Should be the same text", "address private owner;", this.visitor.export( ) );
	}

	@Test
	public void testVisitStateVariableElement_WithNameExternal_CorrectStringReturned( )
	{
		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.ADDRESS, "owner" )
																 .isExternal( )
																 .build( );

		stateVariable.accept( this.visitor );
		assertEquals( "Should be the same text", "address external owner;", this.visitor.export( ) );
	}

	@Test
	public void testVisitStateVariableElement_WithNameInternal_CorrectStringReturned( )
	{
		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.ADDRESS, "owner" )
																 .isInternal( )
																 .build( );

		stateVariable.accept( this.visitor );
		assertEquals( "Should be the same text", "address internal owner;", this.visitor.export( ) );
	}

	@Test
	public void testVisitStateVariableElement_AsConstant_CorrectStringReturned( )
	{
		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.UINT, "amount" )
																 .isPrivate( )
																 .isConstant()
																 .addInitialization( "5" )
																 .build( );

		stateVariable.accept( this.visitor );
		assertEquals( "Should be the same text", "uint private constant amount = 5;", this.visitor.export( ) );
	}

	@Test
	public void testVisitStateVariableElement_WithNameAndInitialization_CorrectStringReturned( )
	{
		StateVariableElement stateVariable = StateVariableElement.builder( DataTypeElement.UINT, "amount" )
																 .isInternal( )
																 .addInitialization( "5" )
																 .build( );

		stateVariable.accept( this.visitor );
		assertEquals( "Should be the same text", "uint internal amount = 5;", this.visitor.export( ) );
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
