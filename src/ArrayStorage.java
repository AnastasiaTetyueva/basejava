/**
 * Array based storage for Resumes
 */
import java.util.Arrays;

public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int resumeCount = 0;

    void clear() {
        for (int i = 0; i < resumeCount; i++) {
            storage[i] = null;
        }
        resumeCount = 0;
    }
    
    void save(Resume r) {
        if (resumeCount == storage.length) {
            System.out.println("В хранилище резюме нет свободного места!");
        } else {
            storage[resumeCount++] = r;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < storage.length; i++) if (storage[i].uuid.equals(uuid)) return storage[i];
        return null;
    }

    void delete(String uuid) {
        int index = -1;
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new NullPointerException("Резюме не найдено");
        }
        for (int i = index; i < resumeCount - 1; i++) {
            storage[i] = storage[i + 1];
        }
        resumeCount--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, resumeCount);
    }

    int size() {
        return resumeCount;
    }
}
