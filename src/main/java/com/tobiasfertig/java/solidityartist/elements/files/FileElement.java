package com.tobiasfertig.java.solidityartist.elements.files;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.util.LinkedHashSet;
import java.util.Set;

public class FileElement implements SolidityElement
{
	private final Set<PragmaElement> pragmaDirectives;
	private final Set<ImportElement> importStatements;
	private final Set<InterfaceElement> interfaces;
	private final Set<LibraryElement> libraries;
	private final Set<ContractElement> contracts;

	private FileElement( Builder builder )
	{
		this.pragmaDirectives = builder.pragmaDirectives;
		this.importStatements = builder.importStatements;
		this.interfaces = builder.interfaces;
		this.libraries = builder.libraries;
		this.contracts = builder.contracts;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public Set<PragmaElement> getPragmaDirectives( )
	{
		return pragmaDirectives;
	}

	public Set<ImportElement> getImportStatements( )
	{
		return importStatements;
	}

	public Set<InterfaceElement> getInterfaces( )
	{
		return interfaces;
	}

	public Set<LibraryElement> getLibraries( )
	{
		return libraries;
	}

	public Set<ContractElement> getContracts( )
	{
		return contracts;
	}

	public static Builder builder( )
	{
		return new Builder( );
	}

	public static final class Builder
	{
		private final Set<PragmaElement> pragmaDirectives = new LinkedHashSet<>( );
		private final Set<ImportElement> importStatements = new LinkedHashSet<>( );
		private final Set<InterfaceElement> interfaces = new LinkedHashSet<>( );
		private final Set<LibraryElement> libraries = new LinkedHashSet<>( );
		private final Set<ContractElement> contracts = new LinkedHashSet<>( );

		private Builder( )
		{

		}

		public Builder addPragmaDirective( PragmaElement pragmaDirective )
		{
			this.pragmaDirectives.add( pragmaDirective );
			return this;
		}

		public Builder addImportStatement( ImportElement importStatement )
		{
			this.importStatements.add( importStatement );
			return this;
		}

		public Builder addInterface( InterfaceElement interfaceElement )
		{
			this.interfaces.add( interfaceElement );
			return this;
		}

		public Builder addLibrary( LibraryElement library )
		{
			this.libraries.add( library );
			return this;
		}

		public Builder addContract( ContractElement contract )
		{
			this.contracts.add( contract );
			return this;
		}

		public FileElement build( )
		{
			return new FileElement( this );
		}
	}
}
