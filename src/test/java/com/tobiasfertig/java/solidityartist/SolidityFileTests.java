package com.tobiasfertig.java.solidityartist;

import com.tobiasfertig.java.solidityartist.elements.datatypes.DataTypeElement;
import com.tobiasfertig.java.solidityartist.elements.files.*;
import com.tobiasfertig.java.solidityartist.elements.typedeclarations.UsingForElement;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class SolidityFileTests
{
	private FileElement fileElement;

	@Before
	public void setUp( )
	{
		PragmaElement pragma = PragmaElement.versionBuilder( ).addVersion( "^0.5.10" ).build( );
		ImportElement importElement = ImportElement.builder( "./IERC20.sol" ).build( );

		InterfaceElement interfaceElement = InterfaceElement.builder( "IA" ).build( );
		InterfaceElement interfaceElement1 = InterfaceElement.builder( "IB" ).build( );

		LibraryElement libraryElement = LibraryElement.builder( "LA" ).build( );
		LibraryElement libraryElement1 = LibraryElement.builder( "LB" ).build( );

		UsingForElement usingFor = UsingForElement.builder( "LA", DataTypeElement.UINT256 ).build( );
		UsingForElement usingFor1 = UsingForElement.builder( "LB", DataTypeElement.ADDRESS ).build( );
		ContractElement contractElement = ContractElement.builder( "A" )
														 .addInheritedContract( "IA" )
														 .addInheritedContract( "IB" )
														 .addUsingForDeclaration( usingFor )
														 .addUsingForDeclaration( usingFor1 )
														 .build( );

		this.fileElement = FileElement.builder( )
									  .addPragmaDirective( pragma )
									  .addImportStatement( importElement )
									  .addInterface( interfaceElement )
									  .addInterface( interfaceElement1 )
									  .addLibrary( libraryElement )
									  .addLibrary( libraryElement1 )
									  .addContract( contractElement )
									  .build( );
	}

	@Test
	public void testSaveToFile_WritesSampleContract_CorrectFileWritten( )
	{
		SolidityFile solidityFile = new SolidityFile( "A.sol", "./contracts", this.fileElement );

		try
		{
			solidityFile.saveToFile( );
		}
		catch ( IOException e )
		{
			e.printStackTrace( );
		}
	}
}
