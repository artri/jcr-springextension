/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.extensions.jcr.jackrabbit.ocm.components.impl;

import org.apache.jackrabbit.ocm.query.Filter;
import org.apache.jackrabbit.ocm.query.Query;
import org.apache.jackrabbit.ocm.query.QueryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.jcr.jackrabbit.ocm.JcrMappingTemplate;
import org.springframework.extensions.jcr.jackrabbit.ocm.components.NewsService;
import org.springframework.extensions.jcr.jackrabbit.ocm.model.News;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Default implementation for {@link org.springframework.extensions.jcr.jackrabbit.ocm.components.ArticleService}
 *
 * @author <a href="mailto:christophe.lombart@sword-technologies.com">Lombart Christophe </a>
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NewsServiceImpl implements NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    private final JcrMappingTemplate jcrMappingtemplate;

    @Autowired
    public NewsServiceImpl(JcrMappingTemplate template) {
        this.jcrMappingtemplate = template;
    }

    public void createNews(News news) {
        jcrMappingtemplate.insert(news);
        jcrMappingtemplate.save();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Collection<News> getNews() {
        
        QueryManager queryManager = jcrMappingtemplate.createQueryManager();
        Filter filter = queryManager.createFilter(News.class);

        Query query = queryManager.createQuery(filter);
        return (Collection<News>) jcrMappingtemplate.getObjects(query);
    }
}
