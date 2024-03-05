package com.apex.tech3.wallt_app.services;

import com.apex.tech3.wallt_app.Helpers;
import com.apex.tech3.wallt_app.exceptions.AuthorizationException;
import com.apex.tech3.wallt_app.exceptions.EntityDuplicateException;
import com.apex.tech3.wallt_app.exceptions.EntityNotFoundException;
import com.apex.tech3.wallt_app.models.Card;
import com.apex.tech3.wallt_app.models.User;
import com.apex.tech3.wallt_app.repositories.CardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class CardServiceTests {
    @Mock
    CardRepository mockRepository;
    @InjectMocks
    CardServiceImpl service;

    @Test
    public void getAll_Should_CallRepository_When_MethodCalled() {
        service.getAll();

        Mockito.verify(mockRepository, Mockito.times(1)).findAll();
    }


    @Test
    public void getById_Should_CallRepository_When_MethodCalled() {
        Card mockCard = Helpers.createMockCard();
        Mockito.when(mockRepository.findByIdAndIsActiveTrue(1)).thenReturn(mockCard);

        service.getById(1);

        Mockito.verify(mockRepository, Mockito.times(2)).findByIdAndIsActiveTrue(1);
    }

    @Test
    public void getById_Should_Throw_When_CardDoesNotExist() {
        Mockito.when(mockRepository.findByIdAndIsActiveTrue(1)).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(1));
    }

    @Test
    public void getByHolderId_Should_CallRepository_When_MethodCalled() {

        service.getByHolderId(1);

        Mockito.verify(mockRepository, Mockito.times(1)).findByHolderId(1);
    }

    @Test
    public void getByHolderId_Should_Throw_When_CardDoesNotExist() {
        Mockito.when(mockRepository.findByIdAndIsActiveTrue(1)).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(1));
    }

    @Test
    public void create_Should_CallRepository_When_MethodCalled() {
        Card mockCard = Helpers.createMockCard();

        service.create(mockCard, mockCard.getHolder());

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockCard);
    }

    @Test
    public void create_ShouldThrow_When_CardNumberNotUnique() {
        Card mockCard = Helpers.createMockCard();

        Mockito.when(mockRepository.existsByNumber(mockCard.getNumber())).thenReturn(true);

        Assertions.assertThrows(EntityDuplicateException.class, () -> service.create(mockCard, Helpers.createMockUser()));
    }

    @Test
    public void update_Should_CallRepository_When_MethodCalled() {
        Card mockCard = Helpers.createMockCard();

        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockCard));

        service.update(mockCard, mockCard.getHolder());

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockCard);
    }

    @Test
    public void update_Should_Throw_When_CardDoesNotExist() {
        Card mockCard = Helpers.createMockCard();

        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.update(mockCard, Helpers.createMockUser()));
    }

    @Test
    public void update_Should_Throw_When_UserNotAuthorized() {
        Card mockCard = Helpers.createMockCard();
        User holder = Helpers.createMockUser();
        holder.setId(2);
        mockCard.setHolder(holder);
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockCard));

        Assertions.assertThrows(AuthorizationException.class, () -> service.update(mockCard, Helpers.createMockUser()));
    }

    @Test
    public void update_Should_Throw_When_CardNumberNotUnique() {
        Card mockCard = Helpers.createMockCard();

        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockCard));
        Mockito.when(mockRepository.existsByNumber(mockCard.getNumber())).thenReturn(true);

        Assertions.assertThrows(EntityDuplicateException.class, () -> service.update(mockCard, Helpers.createMockUser()));
    }

    @Test
    public void deactivate_Should_CallRepository_When_MethodCalled() {
        Card mockCard = Helpers.createMockCard();
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockCard));

        service.deactivate(1, mockCard.getHolder());

        Mockito.verify(mockRepository, Mockito.times(1)).save(mockCard);
    }

    @Test
    public void deactivate_Should_Throw_When_CardDoesNotExist() {
        Card mockCard = Helpers.createMockCard();

        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deactivate(1, Helpers.createMockUser()));
    }

    @Test
    public void deactivate_Should_Throw_When_UserNotAuthorized() {
        Card mockCard = Helpers.createMockCard();
        User holder = Helpers.createMockUser();
        holder.setId(2);
        mockCard.setHolder(holder);
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(mockCard));

        Assertions.assertThrows(AuthorizationException.class, () -> service.deactivate(1, Helpers.createMockUser()));
    }

    @Test
    public void delete_Should_CallRepository_When_MethodCalled() {
        service.delete(1);

        Mockito.verify(mockRepository, Mockito.times(1)).deleteById(1);
    }

//    @Test
//    public void getById_Should_CallRepository_When_MethodCalled() {
//        service.getById(1);
//
//        Mockito.verify(mockRepository, Mockito.times(1)).getById(1);
//    }
//
//
//    @Test
//    public void getByContent_Should_CallRepository_When_MethodCalled() {
//        service.getByContent("title");
//
//        Mockito.verify(mockRepository, Mockito.times(1)).getByContent("title");
//    }
//
//    @Test
//    public void getByUserId_Should_CallRepository_When_MethodCalled() {
//        service.getByUserId(1);
//
//        Mockito.verify(mockRepository, Mockito.times(1)).getByUserId(1);
//    }
//
//    @Test
//    public void create_Should_Throw_When_TitleNotUnique() {
//        Post mockPost = createMockPost();
//
//        Mockito.when(mockRepository.getByTitle(mockPost.getTitle())).thenReturn(mockPost);
//
//        Assertions.assertThrows(EntityDuplicateException.class, () -> service.create(mockPost));
//    }
//
//    @Test
//    public void create_Should_CallRepository_When_TitleUnique() {
//        Post mockPost = createMockPost();
//
//        Mockito.when(mockRepository.getByTitle(mockPost.getTitle()))
//                .thenThrow(EntityNotFoundException.class);
//
//        service.create(mockPost);
//
//        Mockito.verify(mockRepository, Mockito.times(1)).create(mockPost);
//    }
//
//    @Test
//    public void update_Should_Throw_When_UserNotAdminOrRequester() {
//        Post mockPost = createMockPost();
//        User mockUser = createMockUser();
//        User mockUser2 = createMockUser();
//        mockUser2.setId(2);
//        mockPost.setCreatedBy(mockUser2);
//
//        Assertions.assertThrows(AuthorizationException.class, () -> service.update(mockPost, mockUser));
//    }
//
//    @Test
//    public void update_Should_CallRepository_When_UserIsRequester() {
//        Post mockPost = createMockPost();
//        User mockUser = createMockUser();
//
//        service.update(mockPost, mockUser);
//
//        Mockito.verify(mockRepository, Mockito.times(1)).update(mockPost);
//    }
//
//    @Test
//    public void update_Should_CallRepository_When_UserIsAdmin() {
//        Post mockPost = createMockPost();
//        User mockAdmin = createMockAdmin();
//        mockAdmin.setId(2);
//
//        service.update(mockPost, mockAdmin);
//
//        Mockito.verify(mockRepository, Mockito.times(1)).update(mockPost);
//    }
//
//    @Test
//    public void archive_Should_Throw_When_UserNotAdminOrRequester() {
//        Post mockPost = createMockPost();
//        User mockUser = createMockUser();
//        mockUser.setId(2);
//        Mockito.when(mockRepository.get(1)).thenReturn(mockPost);
//
//        Assertions.assertThrows(AuthorizationException.class, () -> service.archive(1, mockUser));
//    }
//
//    @Test
//    public void archive_Should_CallRepository_When_UserIsRequester() {
//        Post mockPost = createMockPost();
//        User mockUser = createMockUser();
//        Mockito.when(mockRepository.get(1)).thenReturn(mockPost);
//
//        service.archive(1, mockUser);
//
//        Mockito.verify(mockRepository, Mockito.times(1)).archive(1);
//
//    }
//
//    @Test
//    public void archive_Should_CallRepository_When_UserIsAdmin() {
//        User mockAdmin = createMockAdmin();
//
//        service.archive(1, mockAdmin);
//
//        Mockito.verify(mockRepository, Mockito.times(1)).archive(1);
//    }
//
//    @Test
//    public void like_Should_CallRepository_When_MethodCalled() {
//        service.like(createMockUser(), 1);
//
//        Mockito.verify(mockRepository, Mockito.times(1)).likeDislike(1, 1, "LIKE");
//    }
//
//    @Test
//    public void dislike_Should_CallRepository_When_MethodCalled() {
//        service.dislike(createMockUser(), 1);
//
//        Mockito.verify(mockRepository, Mockito.times(1)).likeDislike(1, 1, "DISLIKE");
//    }
//
//    @Test
//    public void associateTagWithPost_ShouldIncreaseTagSetSize_WhenArgumentsAreValid() {
//        Tag tag = createMockTag();
//        tag.setTagId(2);
//        Post post = createMockPost();
//
//        Mockito.when(mockRepository.get(Mockito.anyInt())).thenReturn(post);
//        Mockito.when(mockTagRepository.getById(Mockito.anyInt())).thenReturn(tag);
//
//        service.associateTagWithPost(post.getId(), tag.getTagId());
//
//        Assertions.assertEquals(2, post.getTags().size());
//
//        Mockito.verify(mockRepository, Mockito.times(1)).update(post);
//    }
//
//    @Test
//    public void associateTagWithPost_Should_Throw_When_PostDoesNotExist() {
//        Tag tag = createMockTag();
//
//        Mockito.when(mockRepository.get(Mockito.anyInt()))
//                .thenThrow(EntityNotFoundException.class);
//
//        Assertions.assertThrows(EntityNotFoundException.class, ()-> service.associateTagWithPost(1,1));
//
//    }
//
//    @Test
//    public void associateTagWithPost_Should_Throw_When_TagDoesNotExist() {
//        Post post = createMockPost();
//
//        Mockito.when(mockTagRepository.getById(Mockito.anyInt()))
//                .thenThrow(EntityNotFoundException.class);
//
//        Assertions.assertThrows(EntityNotFoundException.class, ()-> service.associateTagWithPost(1, 1));
//
//    }
//
//    @Test
//    public void disassociateTagWithPost_ShouldDecreaseTagSetSize_WhenArgumentsAreValid() {
//        Post post = createMockPost();
//        Tag tag = post.getTags().stream().findFirst().get();
//        Mockito.when(mockRepository.get(Mockito.anyInt())).thenReturn(post);
//        Mockito.when(mockTagRepository.getById(Mockito.anyInt())).thenReturn(tag);
//
//        service.dissociateTagWithPost(post.getId(), tag.getTagId());
//
//        Assertions.assertEquals(0, post.getTags().size());
//
//        Mockito.verify(mockRepository, Mockito.times(1)).update(post);
//    }
//
//    @Test
//    public void dissociateTagWithPost_Should_Throw_When_TagNotInPost() {
//        Post post = createMockPost();
//        Tag tag = createMockTag();
//        tag.setTagId(2);
//        tag.setName("MockTag2");
//        Mockito.when(mockRepository.get(Mockito.anyInt())).thenReturn(post);
//        Mockito.when(mockTagRepository.getById(Mockito.anyInt())).thenReturn(tag);
//        Assertions.assertThrows(EntityNotFoundException.class, () -> service.dissociateTagWithPost(post.getId(), tag.getTagId()));
//
//    }
}
