package com.blog.app.support;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("pegdown")
public class PageDownMarkdownService {
}
