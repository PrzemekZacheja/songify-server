package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

class InMemorySongRepository implements SongRepository {

	final Map<Long, Song> db = new HashMap<>();
	final AtomicLong id = new AtomicLong();

	@Override
	public Song save(final Song song) {
		long id = this.id.getAndIncrement();
		song.setId(id);
		db.put(song.getId(), song);
		return song;
	}

	@Override
	public List<Song> findAll(final Pageable pageable) {
		return db.values()
		         .stream()
		         .toList();
	}

	@Override
	public Optional<Song> findById(final Long id) {
		return Optional.ofNullable(db.get(id));
	}

	@Override
	public void deleteById(final Long id) {
		db.remove(id);
	}

	@Override
	public void deleteSongsByIds(final Collection<Long> ids) {
		ids.forEach(this::deleteById);
	}

	@Override
	public Song updateById(final String name, final Instant releaseDate, final Long duration, final SongLanguage language, final Long id) {
		Song song = db.get(id);
		song.setName(name);
		song.setReleaseDate(releaseDate);
		song.setDuration(duration);
		song.setLanguage(language);
		return song;
	}

	@Override
	public List<Song> findByGenre_Id(final Long id) {
		return db.values()
		         .stream()
		         .filter(song -> song.getGenre()
		                             .getId()
		                             .equals(id))
		         .toList();
	}

}