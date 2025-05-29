package progetto.mp.vannacci.serena.library.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

import progetto.mp.vannacci.serena.library.service.LibraryVisitor;

public final class LibrarySection extends LibraryComponent {

	private final Collection<LibraryComponent> components;

	public LibrarySection(String sectionName, Collection<LibraryComponent> components) {
		super(sectionName);
		this.components = components;
	}

	public void addComponent(LibraryComponent component) {
		components.add(component);
	}

	public void removeComponent(LibraryComponent component) {
		components.remove(component);
	}

	public Optional<LibraryComponent> findComponentByName(String name) {
		return components.stream()
				.filter(component -> component.getLibraryComponentName().equals(name))
				.findFirst();
	}

	public Iterator<LibraryComponent> iterator() {
		return components.iterator();
	}

	@Override
	public <T> T accept(LibraryVisitor<T> visitor) {
		return visitor.visitLibrarySection(this);
	}

	@Override
	protected String buildShareContent() {
		return getLibraryComponentName() + "\n"
				+ components.stream()
				.map(c -> " - " + c.buildShareContent())
				.collect(Collectors.joining("\n"));
	}

	@Override
	public int calculateSizeInMB() {
		return components.stream()
				.mapToInt(LibraryComponent::calculateSizeInMB)
				.sum();
	}

	Collection<LibraryComponent> getComponents() {
		return components;
	}

}