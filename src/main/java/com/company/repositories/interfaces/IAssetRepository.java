package com.company.repositories.interfaces;

import com.company.models.Asset;
import java.util.List;

public interface IAssetRepository {
    boolean create(Asset a);
    List<Asset> getAll();
    Asset getById(int id);
}
